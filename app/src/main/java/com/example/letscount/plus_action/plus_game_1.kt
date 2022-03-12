package com.example.letscount.plus_action

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.ContactsContract
import android.provider.Settings.*
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.util.*
import java.util.jar.Manifest
import kotlin.random.Random
import androidx.core.app.ActivityCompat.startActivityForResult

import android.provider.Settings
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.navigation.fragment.findNavController
import com.example.letscount.R
import java.io.File
import java.io.FileReader
import java.io.FileWriter
import java.util.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [show_memes5.newInstance] factory method to
 * create an instance of this fragment.
 */

public var score = 0

class show_memes5 : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    private var result: TextView? = null
    private var score_text: TextView? = null
    private var icmicro: ImageView? = null
    public var score = 0
    private var flag: Int = 0
    private var randomValue1 = Random.nextInt(0, 10)
    private var randomValue2 = Random.nextInt(0, 10)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val v = inflater.inflate(R.layout.fragment_plus_game_1, container, false)
        val number1: TextView = v.findViewById(R.id.number1)
        val number2: TextView = v.findViewById(R.id.number2)
        icmicro = v.findViewById(R.id.ic_micro)
        result = v.findViewById(R.id.result)
        randomValue1 = Random.nextInt(0, 10)
        randomValue2 = Random.nextInt(0, 10)
        number1.text = randomValue1.toString()
        number2.text = randomValue2.toString()
        icmicro?.setOnClickListener {
            if (flag == 0) {
                flag = 1
                checkAudioPermission()
                icmicro?.setImageResource(R.drawable.ic_mic_subst)
                Toast.makeText(requireActivity(), "Say the answer", Toast.LENGTH_SHORT).show()
                startSpeechToText()
            }
        }
        return v
    }

    private fun checkAudioPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {  // M = 23
            if (ContextCompat.checkSelfPermission(
                    requireActivity(),
                    "android.permission.RECORD_AUDIO"
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                val intent = Intent(
                    Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                    Uri.parse("package:com.programmingtech.offlinespeechtotext")
                )
                startActivity(intent)
                Toast.makeText(requireActivity(), "Allow Microphone Permission", Toast.LENGTH_SHORT)
                    .show()
            }
        }
    }

    private fun startSpeechToText() {
        val speechRecognizer = SpeechRecognizer.createSpeechRecognizer(requireActivity())
        val speechRecognizerIntent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        speechRecognizerIntent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        speechRecognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())

        speechRecognizer.setRecognitionListener(object : RecognitionListener {
            override fun onReadyForSpeech(bundle: Bundle?) {}
            override fun onBeginningOfSpeech() {}
            override fun onRmsChanged(v: Float) {}
            override fun onBufferReceived(bytes: ByteArray?) {}
            override fun onEndOfSpeech() {
                icmicro?.setImageResource(R.drawable.ic_mic)
                flag = 0
            }

            override fun onError(i: Int) {}

            override fun onResults(bundle: Bundle) {
                val results = bundle.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION)
                if (results != null) {
                    var toClear: String = results[0]
                    var cleared: String = ""
                    for (i in toClear.indices) {
                        if (toClear[i].toInt() in 48..57) {
                            cleared += toClear[i]
                        } else {
                            if (cleared.isNotEmpty())
                                break
                        }
                    }
                    result?.text = cleared
                    if (cleared == (randomValue1 + randomValue2).toString()) {
                        findNavController().navigate(R.id.action_plus_game_to_plus_game_2)
                    }
                }
            }

            override fun onPartialResults(bundle: Bundle) {}
            override fun onEvent(i: Int, bundle: Bundle?) {}

        })
        speechRecognizer.startListening(speechRecognizerIntent)
    }


    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment show_memes5.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            show_memes5().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}
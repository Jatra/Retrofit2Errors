package uk.co.jatra.retrofit2errors

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread
import io.reactivex.disposables.CompositeDisposable
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.HttpException
import uk.co.jatra.retrofit2errors.api.ApiAdapter.Companion.adapter
import java.io.IOException
import java.net.ConnectException
import java.net.UnknownHostException


class MainActivity : AppCompatActivity() {

    private val disposables: CompositeDisposable = CompositeDisposable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        good.setOnClickListener {
            disposables.add(adapter.getNames()
                .observeOn(mainThread())
                .subscribe(
                    { value -> handleData(value) },
                    { error -> handleError(error) }
                )
            )
        }
        bad.setOnClickListener {
            disposables.add(adapter.badRequest()
                .observeOn(mainThread())
                .subscribe(
                    { value -> handleData(value) },
                    { error -> handleError(error) }
                )
            )
        }
        notFound.setOnClickListener {
            disposables.add(adapter.notFound()
                .observeOn(mainThread())
                .subscribe(
                    { value -> handleData(value) },
                    { error -> handleError(error) }
                )
            )
        }
        wrongType.setOnClickListener {
            disposables.add(adapter.wrongType()
                .observeOn(mainThread())
                .subscribe(
                    { value -> handleData(value) },
                    { error -> handleError(error) }
                )
            )
        }
        notAList.setOnClickListener {
            disposables.add(adapter.defineReturnToBeAListWhenItIsnt()
                .observeOn(mainThread())
                .subscribe(
                    {  },
                    { error -> handleError(error) }
                )
            )
        }
        notAPrimitve.setOnClickListener {
            disposables.add(adapter.defineReturnToBeABaseTypeWhenItIsAnObject()
                .observeOn(mainThread())
                .subscribe(
                    {  },
                    { error -> handleError(error) }
                )
            )
        }
    }

    override fun onPause() {
        disposables.dispose()
        super.onPause()
    }

    private fun handleData(value: Any) {
        responseCode.text = ""
        exceptionType.text = ""
        response.text = ""
        response.text = ""
        message.text = ""
        body.text = value.toString()

        Log.d("MAIN", "Data = $value")
    }

    private fun handleError(error: Throwable?) {
        responseCode.text = ""
        exceptionType.text = "${error!!::class.java.simpleName}"
        response.text = ""
        response.text = ""
        message.text = ""
        body.text = ""

        when (error) {
            is HttpException -> {
                Log.e("MAIN", "HttpException code ${error.code()}")
                Log.e("MAIN", "HttpException message ${error.message()}")
                Log.e("MAIN", "HttpException response ${error.response()}")
                responseCode.text = "${error.code()}"
                response.text = "${error.response()}"
            }
            is UnknownHostException -> {
                Log.e("MAIN", "UnknownHost: $error", error)
                body.text = "$error"
            }
            is ConnectException -> {
                Log.e("MAIN", "ConnectException: $error", error)
                body.text = "$error"
            }
            is IOException -> {
                Log.e("MAIN", "IOException $error", error)
                //IllegalState eg when expecting a Json object and get a String, or a list and get an object
                body.text = "$error"
            }
            is IllegalStateException -> {
                Log.e("MAIN", "IllegalStateException $error", error)
                body.text = "$error"
            }

            else -> {
                Log.e("MAIN", "handleError called with $error", error)
                body.text = "$error"
            }
        }
    }
}

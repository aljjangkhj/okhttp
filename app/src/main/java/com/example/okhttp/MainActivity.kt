package com.example.okhttp

import android.animation.ValueAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.widget.Toast
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import com.google.gson.annotations.SerializedName
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.lang.Exception

class MainActivity : AppCompatActivity() {
    private val okHttpClient : OkHttpClient? = OkHttpClient()
//    private val url : String = "https://jsonplaceholder.typicode.com/"
    private val url : String = "https://api.randomuser.me/"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        okHttpClient?.newCall(Request.Builder().apply { url(url) }.build())?.enqueue(object : Callback{
//            override fun onFailure(call: Call, e: IOException) {
//                Log.e("KHJ", "IOException : $e")
//            }
//
//            override fun onResponse(call: Call, response: Response) {
//                Log.e("KHJ", "response : $response")
//                Log.e("KHJ", response.body()?.string())
//            }
//        })

        val retrofit = Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(createOkHttpClient())
            .build()


        val request = Request.Builder().url(url).build()
        val client = OkHttpClient()

        client.newCall(request).enqueue(object: Callback {
            override fun onResponse(call: Call, response: Response) {
                val body = response.body()?.string()
                println("KHJ1 -> $body")
//                {"results":[{"gender":"male","name":{"title":"Mr","first":"Narek","last":"Immers"},
//                "location":{"street":{"number":9623,"name":"Grote Bogerd"},"city":"Nieuwland","state":"Limburg","country":"Netherlands","postcode":54557,
//                "coordinates":{"latitude":"43.8096","longitude":"-53.3542"},
//                "timezone":{"offset":"+3:30","description":"Tehran"}},
//                "email":"narek.immers@example.com",
//                "login":{"uuid":"a59fa977-a3aa-49bd-bb34-5abfceb4a998","username":"bluefish384",
//                "password":"pooh","salt":"NpNNJUZ9","md5":"0db10ac53e3598b86847916eefaccb4c","sha1":"0460fbdfabcbf4ada3e18ee7834703948b36228d",
//                "sha256":"b0a3ca7a17a9e3cc2474f65d2b7038d2aaeeb9b78653a3f6a7c0f367e6f8b322"},
//                "dob":{"date":"1981-07-01T23:54:07.621Z","age":39},
//                "registered":{"date":"2014-09-13T01:37:38.393Z","age":6},"phone":"(787)-365-5551","cell":"(570)-609-7834",
//                "id":{"name":"BSN","value":"45170048"},
//                "picture":{"large":"https://randomuser.me/api/portraits/men/94.jpg","medium":"https://randomuser.me/api/portraits/med/men/94.jpg",
//                "thumbnail":"https://randomuser.me/api/portraits/thumb/men/94.jpg"},"nat":"NL"}],
//                "info":{"seed":"c37b0524b7ecf524","results":1,"page":1,"version":"1.3"}}

                //파싱 - 이렇게 가져온 데이터를 모델오브젝트로 변환해 줘야 한다.
                val gson = GsonBuilder().create()
                val parser = JsonParser()
                //루트 object와 경로를 찾아서 설정해 주는데 이부분에서 개념이 안 잡혀서 헤메다. 원하는 데이터가 속에 속에 들어 있어서...
                val rootObj = parser.parse(body.toString()).asJsonObject.get("results")
//                println("KHJ2 -> $rootObj")

                val jsonArray = rootObj.asJsonArray.get(0)
//                println("KHJ2.5 -> $jsonArray")


                val jsonArray2 = jsonArray.asJsonObject.get("name")
                val names =  gson.fromJson(jsonArray2, name::class.java)
                println("KHJ2.8 -> $names")

                val books =  gson.fromJson(jsonArray, results::class.java)
                println("KHJ3 -> ${books}")
            }
            override fun onFailure(call: Call, e: IOException) {
                println("리퀘스트 실패")
            }
        })

//        val service = retrofit.create(RetrofitInterface::class.java)
//
//        val call:retrofit2.Call<test> = service.ApiService()
//        call.enqueue(object : retrofit2.Callback<test>{
//
//
//            override fun onFailure(call: retrofit2.Call<test>, t: Throwable) {
//                Toast.makeText(applicationContext,"실패",Toast.LENGTH_SHORT).show()
//                Log.e("fail","fail : $call ///throwable:  $t")
//            }
//
//            override fun onResponse(call: retrofit2.Call<test>, response: retrofit2.Response<test>) {
//                if(response.body()!=null){
//                    Toast.makeText(applicationContext,"성공",Toast.LENGTH_SHORT).show()
//                    Log.e("onResponse","Call : $call ///onResponse:  $response")
//
//
//                    val res = response.body()?.mDatalist
//
//                    val body = response.body()
//                    println(body)
//
//                    //파싱 - 이렇게 가져온 데이터를 모델오브젝트로 변환해 줘야 한다.
//                    val gson = GsonBuilder().create()
//                    val parser = JsonParser()
//                    //루트 object와 경로를 찾아서 설정해 주는데 이부분에서 개념이 안 잡혀서 헤메다. 원하는 데이터가 속에 속에 들어 있어서...
//                    val rootObj = parser.parse(body.toString())
//                        .getAsJsonObject().get("SeoulLibraryBookRentNumInfo")
//                    val books =  gson.fromJson(rootObj, test::class.java)
//                    //썸네일을 위한 추가 작업
//                    println(books.mDatalist[0])
//                }
//            }
//
//        })


//        ValueAnimator animator = new ValueAnimator();
//        int cashBalance = Integer.valueOf(mCashBalanceResObject.cashBalance);
//        animator.setObjectValues(0,cashBalance);
//        animator.addUpdateListener(new AnimatorUpdateListener() {
//            public void onAnimationUpdate(ValueAnimator animation) {
//                mACommon.doAutoResizeTextCash(R.id.layout_cash_charge_title,String.valueOf(animation.getAnimatedValue()));
//            }
//        });
//        animator.setEvaluator(new TypeEvaluator<Integer>() {
//            public Integer evaluate(float fraction, Integer startValue, Integer endValue) {
//                return java.lang.Math.round(startValue + (endValue - startValue) * fraction);
//            }
//        });
//        animator.setDuration(700);
//        animator.start();

        ValueAnimator.ofInt(0, 200).apply {
            duration = 1000
            addUpdateListener { animation ->
                ticker?.text = animation.animatedValue.toString()
            }

            start()
        }
    }


     fun createOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(interceptor)
        return builder.build()
    }
}
data class results(
@SerializedName("gender")  val gender: String,
@SerializedName("city")  val city: String = "",
@SerializedName("state")  val state: String = "",
@SerializedName("country")  val country: String = "",
@SerializedName("postcode")  val postcode: String = "",
@SerializedName("coordinates")  val coordinates: String = "",
@SerializedName("timezone")  val timezone: String = "",
@SerializedName("email")  val email: String = "",
@SerializedName("login")  val login: JSONArray,
@SerializedName("id")  val id: JSONArray,
@SerializedName("picture")  val picture: JSONArray,
@SerializedName("nat")  val nat: String = "",
@SerializedName("info")  val info: String = ""
)
data class name(
    val title: String,
    val first: String,
    val last : String
)

data class MyStar(val gender : String, val name: String, val location : String, val city : String)

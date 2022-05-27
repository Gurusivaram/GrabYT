GrabYT
===============

GrabYt is  simple library which fetches the youtube site's html page using the given video id then it will give you the results in the following JSON format

```
{
  title: “Test title”,
  views: 234234,
  channel_name: “Test Channel”,
  channel_subscribers: “100M”
}
```


Gradle
------
```
dependencies {
    ...
    //GrabYT library
    implementation 'com.github.Gurusivaram:GrabYT:1.0.1'
}
```

Usage
------
```
...
import com.example.grabytdata.GrabYT
...

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        GrabYT.grab("your video id") {
            runOnUiThread {
                binding.tvJsonResult.text = it.toString()
                Log.d("video Data", it.toString())
            }
        }
    }
}
```

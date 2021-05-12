package tutorialwing.com.zxinglibrary

import android.support.v7.app.AppCompatActivity
import android.os.Bundle

class SecondActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        fragmentManager
                .beginTransaction()
                .add(R.id.rootContainer, QRCodeFragment(), "")
                .commit()
    }
}

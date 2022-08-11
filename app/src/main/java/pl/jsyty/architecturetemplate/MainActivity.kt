package pl.jsyty.architecturetemplate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainNavigationFragment = MainNavigationFragment()
        supportFragmentManager.commit {
            replace(R.id.activityFragmentContainer, mainNavigationFragment)
        }
    }
}

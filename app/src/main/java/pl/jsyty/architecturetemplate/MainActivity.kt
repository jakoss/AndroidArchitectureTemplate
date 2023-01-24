package pl.jsyty.architecturetemplate

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.deliveryhero.whetstone.Whetstone
import com.deliveryhero.whetstone.activity.ContributesActivityInjector
import com.deliveryhero.whetstone.app.ContributesAppInjector

@ContributesActivityInjector
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        Whetstone.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val mainNavigationFragment = supportFragmentManager.fragmentFactory.instantiate(
            this.classLoader,
            MainNavigationFragment::class.java.name
        )
        supportFragmentManager.commit {
            replace(R.id.activityFragmentContainer, mainNavigationFragment)
        }
    }
}

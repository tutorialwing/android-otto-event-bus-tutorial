package tutorialwing.com.ottolibrarytutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.squareup.otto.Produce;

public class SecondActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		GlobalBus.getBus().register(this);
		setContentView(R.layout.activity_second);
	}

	@Produce
	public Events.FragmentActivityMessage produceEvent() {
		// Assuming that we are tracking the last messages for this
		// event (i.e. FragmentActivityMessage) and the message is "Hello Tutorialwing"
 		return new Events.FragmentActivityMessage("Hello Tutorialwing");
	}
}

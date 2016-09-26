package tutorialwing.com.ottolibrarytutorial;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

public class MainActivity extends AppCompatActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		addFragment();
	}

	@Override
	protected void onStart() {
		super.onStart();
		GlobalBus.getBus().register(this);
	}

	private void addFragment() {
		getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, new UserFragment()).commit();
	}

	public void sendMessageToFragment(View view) {
		EditText etMessage = (EditText) findViewById(R.id.activityData);
		Events.ActivityFragmentMessage activityFragmentMessageEvent =
				new Events.ActivityFragmentMessage(String.valueOf(etMessage.getText()));
		GlobalBus.getBus().post(activityFragmentMessageEvent);
	}

	@Subscribe
	public void getMessage(Events.FragmentActivityMessage fragmentActivityMessage) {
		TextView messageView = (TextView) findViewById(R.id.message);
		messageView.setText(getString(R.string.message_received) + " " + fragmentActivityMessage.getMessage());

		Toast.makeText(getApplicationContext(),
				getString(R.string.message_main_activity) + " " + fragmentActivityMessage.getMessage(),
				Toast.LENGTH_SHORT).show();
	}

	@Override
	protected void onStop() {
		super.onStop();
		GlobalBus.getBus().unregister(this);
	}
}

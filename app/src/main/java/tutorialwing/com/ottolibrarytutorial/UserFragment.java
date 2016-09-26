package tutorialwing.com.ottolibrarytutorial;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.otto.Subscribe;

public class UserFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragment_user, container, false);
	}

	@Override
	public void onStart() {
		super.onStart();
		GlobalBus.getBus().register(this);
	}

	@Override
	public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);
		setClickListener(view);
	}

	public void setClickListener(final View view) {
		Button btnSubmit = (Button) view.findViewById(R.id.submit);
		btnSubmit.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				sendMessageToActivity();
			}
		});
	}

	private void sendMessageToActivity() {
		EditText etMessage = (EditText) getView().findViewById(R.id.editText);
		Events.FragmentActivityMessage fragmentActivityMessageEvent =
				new Events.FragmentActivityMessage(String.valueOf(etMessage.getText()));

		GlobalBus.getBus().post(fragmentActivityMessageEvent);
	}

	@Subscribe
	public void getMessage(Events.ActivityFragmentMessage activityFragmentMessage) {
		TextView messageView = (TextView) getView().findViewById(R.id.message);
		messageView.setText(getString(R.string.message_received) + " " + activityFragmentMessage.getMessage());

		Toast.makeText(getActivity(),
				getString(R.string.message_fragment) + " " + activityFragmentMessage.getMessage(),
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onStop() {
		super.onStop();
		GlobalBus.getBus().unregister(this);
	}
}

package com.system.onlinegrading;

import androidx.appcompat.app.AppCompatActivity;
import androidx.annotation.*;
import android.app.*;
import android.os.*;
import android.view.*;
import android.view.View.*;
import android.widget.*;
import android.content.*;
import android.content.res.*;
import android.graphics.*;
import android.graphics.drawable.*;
import android.media.*;
import android.net.*;
import android.text.*;
import android.text.style.*;
import android.util.*;
import android.webkit.*;
import android.animation.*;
import android.view.animation.*;
import java.util.*;
import java.util.regex.*;
import java.text.*;
import org.json.*;
import java.util.HashMap;
import java.util.ArrayList;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import com.airbnb.lottie.*;
import android.widget.Button;
import android.widget.EditText;
import android.content.Intent;
import android.net.Uri;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.view.View;
import android.graphics.Typeface;
import com.shashank.sony.fancytoastlib.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class AddActivity extends  AppCompatActivity  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private String uppercase = "";
	private String lowercase = "";
	private String numbers = "";
	private double positions = 0;
	private String all = "";
	private double pos = 0;
	private HashMap<String, Object> map = new HashMap<>();
	private String u = "";
	
	private ArrayList<String> chars = new ArrayList<>();
	
	private ScrollView vscroll1;
	private LinearLayout linear5;
	private LinearLayout linear1;
	private LottieAnimationView lottie1;
	private LinearLayout linear2;
	private LinearLayout linear3;
	private LinearLayout linear4;
	private Button button1;
	private EditText edittext1;
	private EditText edittext2;
	private EditText edittext3;
	private Button button2;
	
	private Intent i = new Intent();
	private FirebaseAuth auth;
	private OnCompleteListener<Void> auth_updateEmailListener;
	private OnCompleteListener<Void> auth_updatePasswordListener;
	private OnCompleteListener<Void> auth_emailVerificationSentListener;
	private OnCompleteListener<Void> auth_deleteUserListener;
	private OnCompleteListener<Void> auth_updateProfileListener;
	private OnCompleteListener<AuthResult> auth_phoneAuthListener;
	private OnCompleteListener<AuthResult> auth_googleSignInListener;
	private OnCompleteListener<AuthResult> _auth_create_user_listener;
	private OnCompleteListener<AuthResult> _auth_sign_in_listener;
	private OnCompleteListener<Void> _auth_reset_password_listener;
	private DatabaseReference user = _firebase.getReference("user");
	private ChildEventListener _user_child_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.add);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		vscroll1 = (ScrollView) findViewById(R.id.vscroll1);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		lottie1 = (LottieAnimationView) findViewById(R.id.lottie1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear3 = (LinearLayout) findViewById(R.id.linear3);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		button1 = (Button) findViewById(R.id.button1);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		edittext2 = (EditText) findViewById(R.id.edittext2);
		edittext3 = (EditText) findViewById(R.id.edittext3);
		button2 = (Button) findViewById(R.id.button2);
		auth = FirebaseAuth.getInstance();
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if (edittext1.getText().toString().equals("") && (edittext2.getText().toString().equals("") && edittext3.getText().toString().equals(""))) {
					FancyToast.makeText(AddActivity.this, "Field is empty", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
				}
				else {
					auth.createUserWithEmailAndPassword(edittext2.getText().toString(), edittext3.getText().toString()).addOnCompleteListener(AddActivity.this, _auth_create_user_listener);
					_showProgressDialog(true, "Adding Account", "Please wait...");
				}
			}
		});
		
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				_GenerateList(all);
				edittext3.setText("");
				for(int _repeat13 = 0; _repeat13 < (int)(11); _repeat13++) {
					edittext3.setText(edittext3.getText().toString().concat(chars.get((int)(SketchwareUtil.getRandom((int)(0), (int)(chars.size() - 1))))));
				}
			}
		});
		
		_user_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		user.addChildEventListener(_user_child_listener);
		
		auth_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task){
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		auth_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		auth_googleSignInListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task){
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		_auth_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				if (_success) {
					map = new HashMap<>();
					map.put("name", edittext1.getText().toString());
					map.put("email", edittext2.getText().toString());
					map.put("pass", edittext3.getText().toString());
					map.put("userid", FirebaseAuth.getInstance().getCurrentUser().getUid());
					map.put("notifid", "null");
					user.child(FirebaseAuth.getInstance().getCurrentUser().getUid()).updateChildren(map);
					edittext1.setText("");
					edittext2.setText("");
					edittext3.setText("");
					map.clear();
					auth.signInWithEmailAndPassword("admin@app.com", "qx_xLik\"MsV").addOnCompleteListener(AddActivity.this, _auth_sign_in_listener);
					FancyToast.makeText(AddActivity.this, "Added Successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
					_showProgressDialog(false, "g", "b");
				}
				else {
					_showProgressDialog(false, "g", "b");
					SketchwareUtil.showMessage(getApplicationContext(), _errorMessage);
				}
			}
		};
		
		_auth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_auth_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	
	private void initializeLogic() {
		vscroll1.setVerticalScrollBarEnabled(false);
		edittext1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins.ttf"), 0);
		edittext2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins.ttf"), 0);
		edittext3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins.ttf"), 0);
		button1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppinsm.ttf"), 0);
		button2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppinsl.ttf"), 0);
		linear2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFF489EFF));
		linear3.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFF489EFF));
		linear4.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFF489EFF));
		button1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFF489EFF));
		button2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFF6DD5FA));
		linear2.setElevation((float)5);
		linear3.setElevation((float)5);
		linear4.setElevation((float)5);
		button2.setElevation((float)5);
		u = "mre6E7VJErWI59EdveWWrPzG6yz1";
		if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
			if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(u)) {
				
			}
			else {
				finish();
			}
		}
		else {
			i.setClass(getApplicationContext(), ChooseActivity.class);
			startActivity(i);
			finish();
		}
		uppercase = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		lowercase = "abcdefghijklmnopqrstuvwxyz";
		numbers = "0123456789";
		all = uppercase.concat(lowercase.concat(numbers));
		positions = SketchwareUtil.getDisplayWidthPixels(getApplicationContext()) / 18.5d;
		_caps();
		lottie1.setAnimation("add-student.json");
		lottie1.setRepeatCount(999);
		lottie1.playAnimation();
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _GenerateList (final String _string) {
		pos = 0;
		chars.clear();
		for(int _repeat24 = 0; _repeat24 < (int)(_string.length()); _repeat24++) {
			chars.add(_string.substring((int)(pos), (int)(pos + 1)));
			pos++;
		}
	}
	
	
	public void _showProgressDialog (final boolean _ifShow, final String _title, final String _message) {
		if (_ifShow) {
			if (prog == null){
				prog = new ProgressDialog(this);
				prog.setMax(100);
				prog.setIndeterminate(true);
				prog.setCancelable(false);
				prog.setCanceledOnTouchOutside(false);
			}
			prog.setTitle(_title);
			prog.setMessage(_message);
			prog.show();
		}
		else {
			if (prog != null){
				prog.dismiss();
			}
		}
	}
	private ProgressDialog prog;
	{
	}
	
	
	public void _caps () {
		edittext1.setRawInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
	}
	
	
	@Deprecated
	public void showMessage(String _s) {
		Toast.makeText(getApplicationContext(), _s, Toast.LENGTH_SHORT).show();
	}
	
	@Deprecated
	public int getLocationX(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[0];
	}
	
	@Deprecated
	public int getLocationY(View _v) {
		int _location[] = new int[2];
		_v.getLocationInWindow(_location);
		return _location[1];
	}
	
	@Deprecated
	public int getRandom(int _min, int _max) {
		Random random = new Random();
		return random.nextInt(_max - _min + 1) + _min;
	}
	
	@Deprecated
	public ArrayList<Double> getCheckedItemPositionsToArray(ListView _list) {
		ArrayList<Double> _result = new ArrayList<Double>();
		SparseBooleanArray _arr = _list.getCheckedItemPositions();
		for (int _iIdx = 0; _iIdx < _arr.size(); _iIdx++) {
			if (_arr.valueAt(_iIdx))
			_result.add((double)_arr.keyAt(_iIdx));
		}
		return _result;
	}
	
	@Deprecated
	public float getDip(int _input){
		return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, _input, getResources().getDisplayMetrics());
	}
	
	@Deprecated
	public int getDisplayWidthPixels(){
		return getResources().getDisplayMetrics().widthPixels;
	}
	
	@Deprecated
	public int getDisplayHeightPixels(){
		return getResources().getDisplayMetrics().heightPixels;
	}
	
}

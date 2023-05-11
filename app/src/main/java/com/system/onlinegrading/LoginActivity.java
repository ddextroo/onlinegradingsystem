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
import android.widget.LinearLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Button;
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


public class LoginActivity extends  AppCompatActivity  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> m = new HashMap<>();
	private String uid = "";
	
	private LinearLayout linear1;
	private ImageView imageview1;
	private LinearLayout linear2;
	private LinearLayout linear5;
	private LinearLayout linear8;
	private LinearLayout linear9;
	private LinearLayout linear4;
	private TextView textview1;
	private TextView textview4;
	private LinearLayout linear6;
	private LinearLayout linear7;
	private EditText edittext2;
	private EditText edittext3;
	private Button button2;
	private TextView textview3;
	
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
		setContentView(R.layout.login);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear1 = (LinearLayout) findViewById(R.id.linear1);
		imageview1 = (ImageView) findViewById(R.id.imageview1);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear5 = (LinearLayout) findViewById(R.id.linear5);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		linear4 = (LinearLayout) findViewById(R.id.linear4);
		textview1 = (TextView) findViewById(R.id.textview1);
		textview4 = (TextView) findViewById(R.id.textview4);
		linear6 = (LinearLayout) findViewById(R.id.linear6);
		linear7 = (LinearLayout) findViewById(R.id.linear7);
		edittext2 = (EditText) findViewById(R.id.edittext2);
		edittext3 = (EditText) findViewById(R.id.edittext3);
		button2 = (Button) findViewById(R.id.button2);
		textview3 = (TextView) findViewById(R.id.textview3);
		auth = FirebaseAuth.getInstance();
		
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				if ((edittext2.getText().toString().length() > 0) && (edittext3.getText().toString().length() > 0)) {
					auth.signInWithEmailAndPassword(edittext2.getText().toString(), edittext3.getText().toString()).addOnCompleteListener(LoginActivity.this, _auth_sign_in_listener);
					_showProgressDialog(true, "Signing in...", "Please wait");
				}
				else {
					FancyToast.makeText(LoginActivity.this, "Field is empty", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
				}
			}
		});
		
		textview3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				i.setClass(getApplicationContext(), ChooseActivity.class);
				startActivity(i);
				finish();
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
				
			}
		};
		
		_auth_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				if (_success) {
					if (getIntent().getStringExtra("u").equals("student")) {
						_showProgressDialog(false, "f", "v");
						i.setClass(getApplicationContext(), ChooseActivity.class);
						startActivity(i);
						finish();
					}
					else {
						if (getIntent().getStringExtra("u").equals("admin")) {
							if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(uid)) {
								_showProgressDialog(false, "f", "v");
								FancyToast.makeText(LoginActivity.this, "Acess Granted.", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
								i.setClass(getApplicationContext(), AdminActivity.class);
								startActivity(i);
								finish();
							}
							else {
								_showProgressDialog(false, "f", "v");
								FancyToast.makeText(LoginActivity.this, "Access Denied", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
								FirebaseAuth.getInstance().signOut();
								finish();
							}
						}
					}
				}
				else {
					_showProgressDialog(false, "f", "v");
					SketchwareUtil.showMessage(getApplicationContext(), _errorMessage);
				}
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
		textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppinsm.ttf"), 0);
		
		textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppinsb.ttf"), 0);
		textview4.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppinsm.ttf"), 0);
		
		edittext2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins.ttf"), 0);
		edittext3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins.ttf"), 0);
		linear6.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFF489EFF));
		linear6.setElevation((float)5);
		linear7.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFF489EFF));
		linear7.setElevation((float)5);
		button2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFF489EFF));
		button2.setElevation((float)5);
		uid = "mre6E7VJErWI59EdveWWrPzG6yz1";
		if (getIntent().getStringExtra("u").equals("admin")) {
			textview4.setText("Welcome Admin");
		}
		else {
			if (getIntent().getStringExtra("u").equals("student")) {
				textview4.setText("Welcome Student");
			}
		}
		if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
			if (FirebaseAuth.getInstance().getCurrentUser().getUid().equals(uid)) {
				i.setClass(getApplicationContext(), AdminActivity.class);
				startActivity(i);
				finish();
			}
			else {
				i.setClass(getApplicationContext(), StudentActivity.class);
				startActivity(i);
				finish();
			}
		}
		else {
			
		}
		String MyAppPackage = new LoginActivity().getClass().getPackage().getName().toString();
		
		String MyPackage = md5(MyAppPackage).toUpperCase();
		if (!MyPackage.equals("75713431D09350FC9C8C27DF0F7CF070")) {
			FancyToast.makeText(LoginActivity.this, "Modified", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
			finish();
		}
		String MyAndroidID = getApplicationInfo().loadLabel(getPackageManager()).toString();
		
		String MyDeviceID = md5(MyAndroidID).toUpperCase();
		if (!MyDeviceID.equals("53C86D662D4B8E38FB944E4E0947DE1B")) {
			FancyToast.makeText(LoginActivity.this, "Modified", FancyToast.LENGTH_LONG, FancyToast.WARNING, true).show();
			finish();
		}
		
	}
	public String md5(String md5) {
		try { java.security.MessageDigest md = java.security.MessageDigest.getInstance("md5");
			byte[] array = md.digest(md5.getBytes("UTF-8"));
			StringBuffer sb = new StringBuffer(); for (int i = 0; i < array.length; ++i) {
				sb.append(Integer.toHexString((array[i] & 0xFF) | 0x100).substring(1,3));
			} return sb.toString();
		} catch (java.security.NoSuchAlgorithmException e)
		{
		}catch(java.io.UnsupportedEncodingException ex)
		{
		} return null;
	}
	{
		
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
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

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
import android.widget.LinearLayout;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Spinner;
import android.widget.ArrayAdapter;
import androidx.recyclerview.widget.*;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.Adapter;
import androidx.recyclerview.widget.RecyclerView.ViewHolder;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.ChildEventListener;
import android.content.Intent;
import android.net.Uri;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;
import com.onesignal.OSPermissionSubscriptionState;
import com.onesignal.OneSignal;
import org.json.JSONObject;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.FirebaseAuth;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import android.widget.AdapterView;
import android.view.View;
import android.text.Editable;
import android.text.TextWatcher;
import android.graphics.Typeface;
import com.shashank.sony.fancytoastlib.*;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.DialogFragment;


public class AddgradeActivity extends  AppCompatActivity  { 
	
	private FirebaseDatabase _firebase = FirebaseDatabase.getInstance();
	
	private HashMap<String, Object> map = new HashMap<>();
	private String gr = "";
	private String str = "";
	private String str1 = "";
	private String w1 = "";
	private String w2 = "";
	private String w3 = "";
	private String w4 = "";
	private String notifid = "";
	
	private ArrayList<String> subject = new ArrayList<>();
	private ArrayList<String> semester = new ArrayList<>();
	private ArrayList<String> grade = new ArrayList<>();
	private ArrayList<String> quarter = new ArrayList<>();
	private ArrayList<HashMap<String, Object>> lmap = new ArrayList<>();
	private ArrayList<String> allgrades = new ArrayList<>();
	
	private LinearLayout linear11;
	private LinearLayout add;
	private LinearLayout view;
	private LinearLayout linear2;
	private LinearLayout linear8;
	private LinearLayout linear9;
	private LinearLayout linear10;
	private Button button1;
	private Button button2;
	private EditText edittext1;
	private EditText edittext2;
	private TextView textview2;
	private EditText edittext3;
	private TextView textview3;
	private Spinner spinner3;
	private TextView textview4;
	private Spinner spinner1;
	private RecyclerView recyclerview1;
	private Button button3;
	
	private DatabaseReference user = _firebase.getReference("user");
	private ChildEventListener _user_child_listener;
	private DatabaseReference grades = _firebase.getReference(""+gr+"");
	private ChildEventListener _grades_child_listener;
	private Intent i = new Intent();
	
	private OnCompleteListener c_onCompleteListener;
	private OSPermissionSubscriptionState os;
	private FirebaseAuth autj;
	private OnCompleteListener<Void> autj_updateEmailListener;
	private OnCompleteListener<Void> autj_updatePasswordListener;
	private OnCompleteListener<Void> autj_emailVerificationSentListener;
	private OnCompleteListener<Void> autj_deleteUserListener;
	private OnCompleteListener<Void> autj_updateProfileListener;
	private OnCompleteListener<AuthResult> autj_phoneAuthListener;
	private OnCompleteListener<AuthResult> autj_googleSignInListener;
	private OnCompleteListener<AuthResult> _autj_create_user_listener;
	private OnCompleteListener<AuthResult> _autj_sign_in_listener;
	private OnCompleteListener<Void> _autj_reset_password_listener;
	@Override
	protected void onCreate(Bundle _savedInstanceState) {
		super.onCreate(_savedInstanceState);
		setContentView(R.layout.addgrade);
		initialize(_savedInstanceState);
		com.google.firebase.FirebaseApp.initializeApp(this);
		initializeLogic();
	}
	
	private void initialize(Bundle _savedInstanceState) {
		
		linear11 = (LinearLayout) findViewById(R.id.linear11);
		add = (LinearLayout) findViewById(R.id.add);
		view = (LinearLayout) findViewById(R.id.view);
		linear2 = (LinearLayout) findViewById(R.id.linear2);
		linear8 = (LinearLayout) findViewById(R.id.linear8);
		linear9 = (LinearLayout) findViewById(R.id.linear9);
		linear10 = (LinearLayout) findViewById(R.id.linear10);
		button1 = (Button) findViewById(R.id.button1);
		button2 = (Button) findViewById(R.id.button2);
		edittext1 = (EditText) findViewById(R.id.edittext1);
		edittext2 = (EditText) findViewById(R.id.edittext2);
		textview2 = (TextView) findViewById(R.id.textview2);
		edittext3 = (EditText) findViewById(R.id.edittext3);
		textview3 = (TextView) findViewById(R.id.textview3);
		spinner3 = (Spinner) findViewById(R.id.spinner3);
		textview4 = (TextView) findViewById(R.id.textview4);
		spinner1 = (Spinner) findViewById(R.id.spinner1);
		recyclerview1 = (RecyclerView) findViewById(R.id.recyclerview1);
		button3 = (Button) findViewById(R.id.button3);
		autj = FirebaseAuth.getInstance();
		
		button1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				OSPermissionSubscriptionState os = OneSignal.getPermissionSubscriptionState();
				
				boolean isEnabled = os.getPermissionStatus().getEnabled();
				boolean isSubscribed = os.getSubscriptionStatus().getSubscribed();
				boolean subscriptionSetting = os.getSubscriptionStatus().getUserSubscriptionSetting();
				String userID = os.getSubscriptionStatus().getUserId();
				String pushToken = os.getSubscriptionStatus().getPushToken();
				
				if (edittext1.getText().toString().equals("")) {
					FancyToast.makeText(AddgradeActivity.this, "Field is empty", FancyToast.LENGTH_LONG, FancyToast.ERROR, true).show();
				}
				else {
					map = new HashMap<>();
					map.put("subject", edittext1.getText().toString());
					map.put("grade", edittext2.getText().toString().concat(textview2.getText().toString().concat(edittext3.getText().toString())));
					map.put("semester", textview3.getText().toString());
					map.put("quarter", textview4.getText().toString());
					map.put("name", getIntent().getStringExtra("name"));
					map.put("uid", getIntent().getStringExtra("uid"));
					grades.push().updateChildren(map);
					edittext1.setText("");
					edittext2.setText("");
					edittext3.setText("");
					map.clear();
					FancyToast.makeText(AddgradeActivity.this, "Added Successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
					if (notifid.equals("null")) {
						
					}
					else {
						w1 = "The system has posted a grade.";
						w2 = notifid;
						w3 = "SSCT ONLINE GRADING";
						w4 = "null";
						if (!true)
						return;
						
						try {
							   JSONObject notificationContent = new JSONObject("{'contents': {'en': '" + w1 + "'}," +
							           "'include_player_ids': ['" + w2 + "'], " +
							           "'headings': {'en': '" + w3 + "'}, " +
							           "'big_picture': '" + w4 + "'}");
							   OneSignal.postNotification(notificationContent, null);
						} catch (org.json.JSONException e) {
							   e.printStackTrace();
						}
					}
				}
			}
		});
		
		button2.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				add.setVisibility(View.GONE);
				view.setVisibility(View.VISIBLE);
			}
		});
		
		edittext2.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.length() > 1) {
					str = _charSeq.substring((int)(0), (int)(1));
					edittext2.setText("");
				}
				if (_charSeq.length() == 0) {
					edittext2.append(str);
					str = "";
				}
				if (_charSeq.length() == 1) {
					edittext3.requestFocus();
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		edittext3.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				final String _charSeq = _param1.toString();
				if (_charSeq.length() > 2) {
					str1 = _charSeq.substring((int)(0), (int)(2));
					edittext3.setText("");
				}
				if (_charSeq.length() == 0) {
					edittext3.append(str);
					str1 = "";
				}
			}
			
			@Override
			public void beforeTextChanged(CharSequence _param1, int _param2, int _param3, int _param4) {
				
			}
			
			@Override
			public void afterTextChanged(Editable _param1) {
				
			}
		});
		
		spinner3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (_position == 0) {
					textview3.setText("1st Semester");
				}
				if (_position == 1) {
					textview3.setText("2nd Semester");
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		spinner1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> _param1, View _param2, int _param3, long _param4) {
				final int _position = _param3;
				if (_position == 0) {
					textview4.setText("1st Quarter");
				}
				if (_position == 1) {
					textview4.setText("2nd Quarter");
				}
			}
			
			@Override
			public void onNothingSelected(AdapterView<?> _param1) {
				
			}
		});
		
		button3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View _view) {
				add.setVisibility(View.VISIBLE);
				view.setVisibility(View.GONE);
			}
		});
		
		_user_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				if (_childKey.equals(getIntent().getStringExtra("uid"))) {
					notifid = _childValue.get("notifid").toString();
				}
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
		
		_grades_child_listener = new ChildEventListener() {
			@Override
			public void onChildAdded(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				grades.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						lmap = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								lmap.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						allgrades.add(_childKey);
						recyclerview1.setAdapter(new Recyclerview1Adapter(lmap));
						recyclerview1.setLayoutManager(new LinearLayoutManager(AddgradeActivity.this));
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onChildChanged(DataSnapshot _param1, String _param2) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				grades.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						lmap = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								lmap.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						recyclerview1.setAdapter(new Recyclerview1Adapter(lmap));
						recyclerview1.setLayoutManager(new LinearLayoutManager(AddgradeActivity.this));
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onChildMoved(DataSnapshot _param1, String _param2) {
				
			}
			
			@Override
			public void onChildRemoved(DataSnapshot _param1) {
				GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
				final String _childKey = _param1.getKey();
				final HashMap<String, Object> _childValue = _param1.getValue(_ind);
				grades.addListenerForSingleValueEvent(new ValueEventListener() {
					@Override
					public void onDataChange(DataSnapshot _dataSnapshot) {
						lmap = new ArrayList<>();
						try {
							GenericTypeIndicator<HashMap<String, Object>> _ind = new GenericTypeIndicator<HashMap<String, Object>>() {};
							for (DataSnapshot _data : _dataSnapshot.getChildren()) {
								HashMap<String, Object> _map = _data.getValue(_ind);
								lmap.add(_map);
							}
						}
						catch (Exception _e) {
							_e.printStackTrace();
						}
						recyclerview1.setAdapter(new Recyclerview1Adapter(lmap));
						recyclerview1.setLayoutManager(new LinearLayoutManager(AddgradeActivity.this));
					}
					@Override
					public void onCancelled(DatabaseError _databaseError) {
					}
				});
			}
			
			@Override
			public void onCancelled(DatabaseError _param1) {
				final int _errorCode = _param1.getCode();
				final String _errorMessage = _param1.getMessage();
				
			}
		};
		grades.addChildEventListener(_grades_child_listener);
		
		autj_updateEmailListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		autj_updatePasswordListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		autj_emailVerificationSentListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		autj_deleteUserListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		autj_phoneAuthListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task){
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		autj_updateProfileListener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		autj_googleSignInListener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> task){
				final boolean _success = task.isSuccessful();
				final String _errorMessage = task.getException() != null ? task.getException().getMessage() : "";
				
			}
		};
		
		_autj_create_user_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_autj_sign_in_listener = new OnCompleteListener<AuthResult>() {
			@Override
			public void onComplete(Task<AuthResult> _param1) {
				final boolean _success = _param1.isSuccessful();
				final String _errorMessage = _param1.getException() != null ? _param1.getException().getMessage() : "";
				
			}
		};
		
		_autj_reset_password_listener = new OnCompleteListener<Void>() {
			@Override
			public void onComplete(Task<Void> _param1) {
				final boolean _success = _param1.isSuccessful();
				
			}
		};
	}
	
	private void initializeLogic() {
		_caps();
		add.setVisibility(View.VISIBLE);
		view.setVisibility(View.GONE);
		edittext1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins.ttf"), 0);
		textview2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins.ttf"), 0);
		textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins.ttf"), 0);
		textview4.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins.ttf"), 0);
		button1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppinsm.ttf"), 0);
		button2.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppinsm.ttf"), 0);
		linear2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFF489EFF));
		button1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFF489EFF));
		button2.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFF489EFF));
		linear8.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFF489EFF));
		linear9.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFF489EFF));
		linear10.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFF489EFF));
		button3.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFF489EFF));
		linear2.setElevation((float)5);
		linear8.setElevation((float)5);
		linear9.setElevation((float)5);
		linear10.setElevation((float)5);
		semester.add("1st Semester");
		semester.add("2nd Semester");
		spinner3.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, semester));
		((ArrayAdapter)spinner3.getAdapter()).notifyDataSetChanged();
		quarter.add("1st Quarter");
		quarter.add("2nd Quarter");
		spinner1.setAdapter(new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, quarter));
		((ArrayAdapter)spinner1.getAdapter()).notifyDataSetChanged();
		grades.removeEventListener(_grades_child_listener);
		gr = "grades/".concat(getIntent().getStringExtra("uid").concat("/"));
		grades =_firebase.getReference(gr);
		grades.addChildEventListener(_grades_child_listener);
		if ((FirebaseAuth.getInstance().getCurrentUser() != null)) {
			
			os = OneSignal.getPermissionSubscriptionState();
			OneSignal.init(AddgradeActivity.this, "326252965581", "44c5ebd1-8c91-42ca-b4d7-362eb3a145dd");
			 OneSignal.getCurrentOrNewInitBuilder()
			.inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification)
			.unsubscribeWhenNotificationsAreDisabled(true).init();
			OSPermissionSubscriptionState os = OneSignal.getPermissionSubscriptionState();
			
			boolean isEnabled = os.getPermissionStatus().getEnabled();
			boolean isSubscribed = os.getSubscriptionStatus().getSubscribed();
			boolean subscriptionSetting = os.getSubscriptionStatus().getUserSubscriptionSetting();
			String userID = os.getSubscriptionStatus().getUserId();
			String pushToken = os.getSubscriptionStatus().getPushToken();
			
			OneSignal.setSubscription(true);
		}
	}
	
	@Override
	protected void onActivityResult(int _requestCode, int _resultCode, Intent _data) {
		
		super.onActivityResult(_requestCode, _resultCode, _data);
		
		switch (_requestCode) {
			
			default:
			break;
		}
	}
	
	public void _caps () {
		edittext1.setRawInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);
	}
	
	
	public class Recyclerview1Adapter extends RecyclerView.Adapter<Recyclerview1Adapter.ViewHolder> {
		ArrayList<HashMap<String, Object>> _data;
		public Recyclerview1Adapter(ArrayList<HashMap<String, Object>> _arr) {
			_data = _arr;
		}
		
		@Override
		public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			LayoutInflater _inflater = (LayoutInflater)getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			View _v = _inflater.inflate(R.layout.ggg, null);
			RecyclerView.LayoutParams _lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
			_v.setLayoutParams(_lp);
			return new ViewHolder(_v);
		}
		
		@Override
		public void onBindViewHolder(ViewHolder _holder, final int _position) {
			View _view = _holder.itemView;
			
			final androidx.cardview.widget.CardView cardview1 = (androidx.cardview.widget.CardView) _view.findViewById(R.id.cardview1);
			final LinearLayout linear1 = (LinearLayout) _view.findViewById(R.id.linear1);
			final TextView textview1 = (TextView) _view.findViewById(R.id.textview1);
			final TextView textview6 = (TextView) _view.findViewById(R.id.textview6);
			final LinearLayout linear2 = (LinearLayout) _view.findViewById(R.id.linear2);
			final LinearLayout linear3 = (LinearLayout) _view.findViewById(R.id.linear3);
			final TextView textview3 = (TextView) _view.findViewById(R.id.textview3);
			final TextView textview4 = (TextView) _view.findViewById(R.id.textview4);
			final Button button1 = (Button) _view.findViewById(R.id.button1);
			
			textview1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppinsm.ttf"), 0);
			button1.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppins.ttf"), 0);
			textview3.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppinssm.ttf"), 0);
			textview4.setTypeface(Typeface.createFromAsset(getAssets(),"fonts/poppinssm.ttf"), 0);
			
			button1.setBackground(new GradientDrawable() { public GradientDrawable getIns(int a, int b) { this.setCornerRadius(a); this.setColor(b); return this; } }.getIns((int)20, 0xFF6DD5FA));
			cardview1.setCardBackgroundColor(0xFF489EFF);
			cardview1.setRadius((float)20);
			cardview1.setCardElevation((float)5);
			cardview1.setPreventCornerOverlap(true);
			cardview1.setUseCompatPadding(true);
			if (lmap.get((int)_position).containsKey("subject")) {
				textview1.setText(lmap.get((int)_position).get("subject").toString());
			}
			if (lmap.get((int)_position).containsKey("grade")) {
				textview6.setText("\"".concat(lmap.get((int)_position).get("grade").toString().concat("\"")));
			}
			if (lmap.get((int)_position).containsKey("semester")) {
				textview3.setText(lmap.get((int)_position).get("semester").toString());
			}
			if (lmap.get((int)_position).containsKey("quarter")) {
				textview4.setText(lmap.get((int)_position).get("quarter").toString());
			}
			button1.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View _view) {
					grades.child(allgrades.get((int)(_position))).removeValue();
					FancyToast.makeText(AddgradeActivity.this, "Deleted Successfully", FancyToast.LENGTH_LONG, FancyToast.SUCCESS, true).show();
				}
			});
		}
		
		@Override
		public int getItemCount() {
			return _data.size();
		}
		
		public class ViewHolder extends RecyclerView.ViewHolder{
			public ViewHolder(View v){
				super(v);
			}
		}
		
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

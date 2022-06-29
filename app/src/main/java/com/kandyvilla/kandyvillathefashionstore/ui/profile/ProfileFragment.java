package com.kandyvilla.kandyvillathefashionstore.ui.profile;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.renderscript.RenderScript;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kandyvilla.kandyvillathefashionstore.R;
import com.kandyvilla.kandyvillathefashionstore.models.userModel;

import java.util.HashMap;
import java.util.Map;

import cn.pedant.SweetAlert.SweetAlertDialog;
import de.hdodenhof.circleimageview.CircleImageView;


public class ProfileFragment extends Fragment {

    CircleImageView profileimg;
    EditText profilename,profileemail,profilenumber,profileaddress;
    Button update;
    FirebaseStorage firebaseStorage;
    FirebaseAuth firebaseAuth;
    FirebaseDatabase firebaseDatabase;
    ProgressBar progressBar;
    SweetAlertDialog sweetAlertDialog;
    FirebaseFirestore firestore;
    RelativeLayout relativeLayout;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_profile, container, false);

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        progressBar = root.findViewById(R.id.progressbar_profile);
        profileimg = root.findViewById(R.id.profile_img);
        profilename = root.findViewById(R.id.profile_name);
        profileemail = root.findViewById(R.id.profile_email);
        profilenumber = root.findViewById(R.id.profile_number);
        profileaddress = root.findViewById(R.id.profile_address);
        update = root.findViewById(R.id.profile_updatebtn);
        relativeLayout = root.findViewById(R.id.profile);
        firestore = FirebaseFirestore.getInstance();



        progressBar.setVisibility(View.VISIBLE);
        relativeLayout.setVisibility(View.GONE);

        firebaseDatabase.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userModel usermodel = snapshot.getValue(userModel.class);

                if(usermodel.getProfileImg() == null){
                    profileimg.setImageDrawable(getResources().getDrawable(R.drawable.user_admin));
                }
                else {
                    Glide.with(getContext()).load(usermodel.getProfileImg()).into(profileimg);
                }
                profilename.setText(usermodel.getName());
                profileemail.setText(usermodel.getEmail());
                profilenumber.setText(usermodel.getMobileno());
                profileaddress.setText(usermodel.getAddress());

                progressBar.setVisibility(View.GONE);
                relativeLayout.setVisibility(View.VISIBLE);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(),"Error"+ error,Toast.LENGTH_SHORT).show();
            }
        });


        profileimg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_GET_CONTENT);
                intent.setType("image/*");
                startActivityForResult(intent,33);
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                update_profile();
            }
        });

        return root;
    }

    private void update_profile() {
        Map<String,Object> map = new HashMap<>();

        map.put("name",profilename.getText().toString());
        map.put("email",profileemail.getText().toString());
        map.put("mobileno",profilenumber.getText().toString());
        map.put("address",profileaddress.getText().toString());

        firebaseDatabase.getReference().child("Users").child(firebaseAuth.getCurrentUser().getUid()).updateChildren(map)
          .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(getContext(), "Success", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                new SweetAlertDialog(getContext(),SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Error Occured !")
                        .show();
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(data.getData() != null){
            Uri profileUri = data.getData();
            profileimg.setImageURI(profileUri);

            final StorageReference reference = firebaseStorage.getReference().child("profile_picture").child(FirebaseAuth.getInstance().getUid());

            reference.putFile(profileUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    Toast.makeText(getContext(),"Profile Image Uploaded Successfully !!",Toast.LENGTH_SHORT).show();
                }
            }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                    Toast.makeText(getContext(),"Uploading....",Toast.LENGTH_LONG).show();

                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            firebaseDatabase.getReference().child("Users").child(FirebaseAuth.getInstance().getUid()).child("profileImg").setValue(uri.toString());
                        }
                    });
                }
            });

        }
    }
}
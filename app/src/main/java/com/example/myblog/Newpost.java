    package com.example.myblog;


    import android.app.ProgressDialog;
    import android.content.ContentResolver;
    import android.content.Context;
    import android.content.Intent;
    import android.content.res.Resources;
    import android.graphics.Bitmap;
    import android.net.Uri;
    import android.os.Bundle;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;

    import android.provider.MediaStore;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.webkit.MimeTypeMap;
    import android.widget.Button;
    import android.widget.EditText;
    import android.widget.ImageView;
    import android.widget.ProgressBar;
    import android.widget.Toast;

    import com.google.android.gms.tasks.OnFailureListener;
    import com.google.android.gms.tasks.OnSuccessListener;
    import com.google.android.gms.tasks.Task;
    import com.google.firebase.auth.FirebaseAuth;
    import com.google.firebase.auth.FirebaseUser;
    import com.google.firebase.database.DatabaseReference;
    import com.google.firebase.database.FirebaseDatabase;
    import com.google.firebase.storage.FirebaseStorage;
    import com.google.firebase.storage.OnProgressListener;
    import com.google.firebase.storage.StorageReference;
    import com.google.firebase.storage.StorageTask;
    import com.google.firebase.storage.UploadTask;
    import com.squareup.picasso.Picasso;

    import java.io.IOException;
    import java.util.UUID;

    import static android.app.Activity.RESULT_OK;


    /**
     * A simple {@link Fragment} subclass.
     */
    public class Newpost extends Fragment implements View.OnClickListener {

        private EditText titleEt,descripEt;
        private Button choseBtn,uploadBtn;
        private ImageView imgView;

        private ProgressBar progressBar;
        private Uri imageUri;

        private FirebaseAuth auth;
        private FirebaseUser user;

        private FirebaseDatabase database;
        private DatabaseReference myRef;
        private DatabaseReference rootRef;
        private DatabaseReference userRef;
        private DatabaseReference userIdRef;
        private DatabaseReference currentUser;

        StorageReference mStorageRef;
        StorageReference myIdRef;
        StorageTask uploadtask;


        private Context context;


        private static final int IMAGE_RQUEST = 1;

        public Newpost() {

        }




        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            auth = FirebaseAuth.getInstance();
            user = auth.getCurrentUser();
            rootRef  = FirebaseDatabase.getInstance().getReference();
            userRef = rootRef.child("user");
            userIdRef = userRef.child(user.getUid());
            myRef = userIdRef.child("Post");
            mStorageRef = FirebaseStorage.getInstance().getReference();

            View view = inflater.inflate(R.layout.fragment_newpost, container, false);
            titleEt = view.findViewById(R.id.titleEt);
            descripEt = view.findViewById(R.id.descripEt);
            imgView = view.findViewById(R.id.imgView);

            choseBtn = view.findViewById(R.id.choseimg);
            uploadBtn = view.findViewById(R.id.uploadBtn);

            choseBtn.setOnClickListener(this);
            uploadBtn.setOnClickListener(this);

            return view; }

        @Override
        public void onClick(View view) {

            switch (view.getId()){

                case R.id.choseimg:
                    openfilechoser();
                    break;

                case R.id.uploadBtn:

                    if(uploadtask!=null && uploadtask.isInProgress()){
                        Toast.makeText(getActivity(),"upload is prograess",Toast.LENGTH_SHORT).show();
                    }else {
                        savefile();

                    }

                    break;

            } }

        public void openfilechoser(){

            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,IMAGE_RQUEST);
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if(requestCode==IMAGE_RQUEST && resultCode==RESULT_OK && data!=null && data.getData()!=null){
                imageUri = data.getData();
                Picasso.get().load(imageUri).into(imgView);
            } }

        public String getFileExtension(Uri imageUri){
           ContentResolver contentResolver = getActivity().getContentResolver();
           MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
           return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
       }

        public void savefile(){

            final String  postTitle = titleEt.getText().toString().trim();
            final String  description = descripEt.getText().toString().trim();

            if(postTitle.isEmpty()){
                Toast.makeText(getActivity(),"fillup postTitle field",Toast.LENGTH_SHORT).show();
            }else if(description.isEmpty()){
                Toast.makeText(getActivity(),"fillup description field",Toast.LENGTH_SHORT).show();
            }else{
                StorageReference ref = mStorageRef.child(System.currentTimeMillis()+"."+getFileExtension(imageUri));
                ref.putFile(imageUri)
                        .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Toast.makeText(getActivity(),"upload successfully storage",Toast.LENGTH_SHORT).show();
                                String keyId = myRef.push().getKey();
                                Task<Uri> task = taskSnapshot.getStorage().getDownloadUrl();
                                while (!task.isSuccessful());
                                Uri downladUri = task.getResult();

                                Postnew postnew = new Postnew(keyId,postTitle,description, downladUri.toString());
                                myRef.child(keyId).setValue(postnew);

                                titleEt.setText(" ");
                                descripEt.setText(" ");
                                Resources resources = getResources();
                                imgView.setImageDrawable(resources.getDrawable(R.drawable.img));

                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception exception) {
                                Toast.makeText(getActivity(),"upload is not successfully storage",Toast.LENGTH_SHORT).show();
                            }
                        });
            }


        }



    }

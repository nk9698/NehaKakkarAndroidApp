package com.knavic.nehakakkar;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class DataAdapter extends RecyclerView.Adapter<DataAdapter.ViewHolder> {
    private ArrayList<AndroidVersion> android_versions;
    private Context context;
    private InterstitialAd mInterstitialAd;
    public DataAdapter(Context context, ArrayList<AndroidVersion> android_versions) {
        this.context = context;
        this.android_versions = android_versions;

    }

    @Override
    public DataAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.raw_layout, viewGroup, false);
        return new ViewHolder(view);
    }



    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {

        Picasso.with(context).load(android_versions.get(i).getAndroid_image_url()).into(viewHolder.img_android);
        viewHolder.sharebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.with(context.getApplicationContext()).load(android_versions.get(i).getAndroid_image_url()).into(new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        Intent intent = new Intent("android.intent.action.SEND");
                        intent.setType("image/*");
                        intent.putExtra("android.intent.extra.STREAM", getlocalBitmapUri(bitmap));
                        final String packageName = context.getPackageName();
                        intent.putExtra(Intent.EXTRA_TEXT," Share This App: http://play.google.com/store/apps/details?id="+packageName);
                        context.startActivity(Intent.createChooser(intent, "share"));

                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {

                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {

                    }
                });
            }
        });
        mInterstitialAd = new InterstitialAd(context);
        mInterstitialAd.setAdUnitId("ca-app-pub-9382057421484176/4482281355");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());
        viewHolder.savebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Picasso.with(context.getApplicationContext())
                        .load(android_versions.get(i).getAndroid_image_url())
                        .into(new Target() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {

                                try {
                                    File mydie = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES).toString() + "/Neha Kakkar");
                                    if (!mydie.exists()) {
                                        mydie.mkdirs();
                                    }
                                    FileOutputStream fileOutputStream = new FileOutputStream(new File(mydie, new Date().toString() + ".jpg"));
                                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
                                    fileOutputStream.flush();
                                    fileOutputStream.close();
                                    if (mInterstitialAd.isLoaded()) {
                                        mInterstitialAd.show();

                                    }
                                    Toast.makeText(context.getApplicationContext(), "Saved in Picture/Neha Kakkar", Toast.LENGTH_LONG).show();
                                } catch (FileNotFoundException e) {
                                    e.printStackTrace();
                                } catch (IOException e2) {
                                    e2.printStackTrace();
                                }
                            }


                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {

                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {

                            }
                        });
            }
        });
    }

    @Override
    public int getItemCount() {
        return android_versions.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageButton savebutton;
        ImageButton sharebutton;
        ImageView img_android;
        public ViewHolder(View view) {
            super(view);

            savebutton =(ImageButton)view.findViewById(R.id.imageButton);
            sharebutton=(ImageButton)view.findViewById(R.id.imageButton2);
            img_android = (ImageView)view.findViewById(R.id.img_android);
        }
    }

    private Uri getlocalBitmapUri(Bitmap bitmap) {

        Uri bmuri = null;
        try {
            File file = new File(Environment.getExternalStorageDirectory() + File.separator + "image.jpg");
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fileOutputStream);
            fileOutputStream.close();
            bmuri = Uri.fromFile(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e2) {
            e2.printStackTrace();
        }
        return bmuri;

    }
}


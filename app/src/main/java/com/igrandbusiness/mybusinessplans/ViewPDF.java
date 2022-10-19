package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.ads.AdSize;
import com.facebook.ads.AdView;
import com.facebook.ads.AudienceNetworkAds;
import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.igrandbusiness.mybusinessplans.utils.Constants;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;

public class ViewPDF extends AppCompatActivity implements OnLoadCompleteListener, OnPageErrorListener {
    PDFView pdfView;
    String pdf,name;
    Uri uri;
    int contentId,contentType = 0;
    CardView progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_p_d_f);
        pdfView = findViewById(R.id.pdfviewer);
        progress = findViewById(R.id.progress);
        pdf = getIntent().getExtras().getString("URI");
        name = getIntent().getExtras().getString("TITLE");
        contentId = Integer.parseInt(getIntent().getExtras().getString("ID"));
        contentType = 3;
        Constants.saveUsageStat(this,contentId,contentType);
        setTitle(name);
        progress.setVisibility(View.VISIBLE);
        uri = Uri.parse(pdf);
        progress.setVisibility(View.VISIBLE);
        FileLoader.with(this).load(pdf,false)
                .fromDirectory("My_Pdfs",FileLoader.DIR_INTERNAL)
                .asFile(new FileRequestListener<File>() {
                    @Override
                    public void onLoad(FileLoadRequest request, FileResponse<File> response) {
                        progress.setVisibility(View.GONE);
                        File file = response.getBody();
                        try {
                            pdfView.fromFile(file)
                                    .defaultPage(0)
                                    .enableAnnotationRendering(true)
                                    .onLoad(ViewPDF.this)
                                    .scrollHandle(new DefaultScrollHandle(ViewPDF.this))
                                    .spacing(3)
                                    .onPageError(ViewPDF.this)
                                    .load();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(ViewPDF.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

    }
    @Override
    public void loadComplete(int nbPages) {
        progress.setVisibility(View.GONE);
    }

    @Override
    public void onPageError(int page, Throwable t) {
        progress.setVisibility(View.GONE);
        Toast.makeText(ViewPDF.this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
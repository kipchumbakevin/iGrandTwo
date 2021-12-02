package com.igrandbusiness.mybusinessplans;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageErrorListener;
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.krishna.fileloader.FileLoader;
import com.krishna.fileloader.listener.FileRequestListener;
import com.krishna.fileloader.pojo.FileResponse;
import com.krishna.fileloader.request.FileLoadRequest;

import java.io.File;

public class ViewMagazine extends AppCompatActivity implements OnLoadCompleteListener, OnPageErrorListener {
    CollapsingToolbarLayout collapsingToolbarLayout;
    PDFView pdfView;
    String pdf,name;
    Uri uri;
    CardView progress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_magazine);
        collapsingToolbarLayout = findViewById(R.id.collapsing_toolbar);
        pdfView = findViewById(R.id.pdfviewer);
        progress = findViewById(R.id.progress);
        pdf = getIntent().getExtras().getString("URI");
        name = getIntent().getExtras().getString("TITLE");
        collapsingToolbarLayout.setTitle(name);
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
                                    .onLoad(ViewMagazine.this)
                                    .scrollHandle(new DefaultScrollHandle(ViewMagazine.this))
                                    .spacing(3)
                                    .onPageError(ViewMagazine.this)
                                    .load();
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(FileLoadRequest request, Throwable t) {
                        progress.setVisibility(View.GONE);
                        Toast.makeText(ViewMagazine.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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
        Toast.makeText(ViewMagazine.this, t.getMessage(), Toast.LENGTH_SHORT).show();
    }
}
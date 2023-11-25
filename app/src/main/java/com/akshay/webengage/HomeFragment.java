package com.akshay.webengage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.method.LinkMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class HomeFragment extends Fragment {
 TextView customerData, analytics,segmentation;
    public HomeFragment() {
        super(R.layout.fragment_home);

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       customerData=view.findViewById(R.id.home_customer_link);
        customerData.setMovementMethod(LinkMovementMethod.getInstance());
        analytics=view.findViewById(R.id.home_analytics_link);
        analytics.setMovementMethod(LinkMovementMethod.getInstance());
        segmentation=view.findViewById(R.id.home_segmentation_link);
        segmentation.setMovementMethod(LinkMovementMethod.getInstance());
    }

    @Override
    public void onResume() {
        super.onResume();
        customerData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://webengage.com/customer-data-platform/"));
                startActivity(browserIntent);
            }
        });
        analytics.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://webengage.com/product-and-revenue-analytics/"));
                startActivity(browserIntent);
            }
        });
        segmentation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent browserIntent = new Intent(Intent.ACTION_VIEW);
                browserIntent.setData(Uri.parse("https://webengage.com/customer-segmentation/"));
                startActivity(browserIntent);
            }
        });

    }
}
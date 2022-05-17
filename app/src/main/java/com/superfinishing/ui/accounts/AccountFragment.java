package com.superfinishing.ui.accounts;

import static com.superfinishing.Workspace.currentUser;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.superfinishing.BuildConfig;
import com.superfinishing.LoginActivity;
import com.superfinishing.R;
import com.superfinishing.admin.AdminActivity;
import com.superfinishing.seller.SellerRegisterActivity;
import com.superfinishing.service.ServiceRegisterActivity;
import com.superfinishing.ui.accounts.customerSupport.CustomerSupportActivity;
import com.superfinishing.ui.accounts.myAddress.MyAddressActivity;
import com.superfinishing.ui.accounts.myOrders.MyOrdersActivity;

public class AccountFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view= inflater.inflate(R.layout.fragment_account, container, false);

        TextView textViewAddress = view.findViewById(R.id.textView51);
        textViewAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MyAddressActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.textView50).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), MyOrdersActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.textView57).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                ShareCompat.IntentBuilder.from((Activity) getContext())
//                        .setType("text/plain")
//                        .setChooserTitle("Chooser title")
//                        .setText("http://play.google.com/store/apps/details?id=" + getContext().getPackageName())
//                        .startChooser();

                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "FreshMart");
                    String shareMessage= "\nDownload Fresh Mart application\n\n";
                    shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, "choose one"));
                } catch(Exception e) {
                    //e.toString();
                }
            }
        });
        view.findViewById(R.id.textView56).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.crkb.banssomart")));
            }
        });
        view.findViewById(R.id.textView55).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), CustomerSupportActivity.class);
                startActivity(intent);
            }
        });

        view.findViewById(R.id.textView66).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getContext(), AdminActivity.class));
            }
        });

        view.findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), LoginActivity.class));
            }
        });
        view.findViewById(R.id.textView59).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), SellerRegisterActivity.class);
                startActivity(intent);
            }
        });
        view.findViewById(R.id.textView60).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getContext(), ServiceRegisterActivity.class);
                startActivity(intent);
            }
        });

        TextView textViewWelcome = view.findViewById(R.id.textView46);
        TextView textViewDesc = view.findViewById(R.id.textView48);
        Button button = view.findViewById(R.id.button);
        ImageView imageViewLogout = view.findViewById(R.id.imageView53);
        TextView textViewLogout = view.findViewById(R.id.textView148);
        textViewLogout.setVisibility(View.GONE);
        imageViewLogout.setVisibility(View.GONE);

        if (currentUser!=null){

            System.out.println("ussssssssssseeeeeeeeeeeeeeeee ");
            FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
            if (user!=null){
                System.out.println("11111111111111111 ");
                if (user.getEmail()!=null){
                    if (!user.getEmail().isEmpty()){
                        textViewWelcome.setText(user.getDisplayName());
                        textViewDesc.setText(user.getEmail().toString());
                        System.out.println("2222222222222222222 ");
                    }
                }
                if (user.getPhoneNumber()!=null){
                    if (!user.getPhoneNumber().isEmpty()){
                        textViewWelcome.setText(user.getDisplayName());
                        textViewDesc.setText(user.getPhoneNumber().toString());
                        System.out.println("33333333333333333333 ");
                    }
                }


                button.setVisibility(View.GONE);
                textViewLogout.setVisibility(View.VISIBLE);
                imageViewLogout.setVisibility(View.VISIBLE);
            }
        }

        textViewLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                Intent intent=new Intent(getContext(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        return view;
    }
}
package com.example.androiddev_badmintoncourtreservation.ui.reservation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androiddev_badmintoncourtreservation.R;
import com.example.androiddev_badmintoncourtreservation.adapter.ReservationsRecyclerAdapter;
import com.example.androiddev_badmintoncourtreservation.database.entity.PlayerEntity;
import com.example.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;
import com.example.androiddev_badmintoncourtreservation.ui.BaseActivity;
import com.example.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;
import com.example.androiddev_badmintoncourtreservation.util.RecyclerViewItemClickListener;
import com.example.androiddev_badmintoncourtreservation.viewmodel.court.CourtListViewModel;
import com.example.androiddev_badmintoncourtreservation.viewmodel.player.PlayerListViewModel;
import com.example.androiddev_badmintoncourtreservation.viewmodel.reservation.ReservationListViewModel;

import java.util.List;

public class ReservationsActivity extends BaseActivity {

    private ReservationsRecyclerAdapter<ReservationEntity> adapter;
    private RecyclerView recyclerView;

    private ReservationListViewModel listViewModel;
    private List<ReservationEntity> reservations;

    private List<PlayerEntity> players;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_reservations, frameLayout);
        navigationView.setCheckedItem(R.id.nav_none);

        setTitle(R.string.reservation_homePage);

        recyclerView = findViewById(R.id.ReservationRv);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        adapter = new ReservationsRecyclerAdapter<>(new RecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                //Edit the reservation
                Intent intent = new Intent(ReservationsActivity.this, CourtReservationActivity.class);
                intent.setFlags(
                        Intent.FLAG_ACTIVITY_NO_ANIMATION |
                                Intent.FLAG_ACTIVITY_NO_HISTORY
                );
                intent.putExtra("reservationId", reservations.get(position).getId());
                startActivity(intent);
            }

            @Override
            public void onItemLongClick(View v, int position) {
                deleteReservationDialog(position);
            }
        });

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        ReservationListViewModel.Factory factoryReservations = new ReservationListViewModel.Factory(getApplication());
        listViewModel = new ViewModelProvider(this, (ViewModelProvider.Factory) factoryReservations).get(ReservationListViewModel.class);
        listViewModel.getReservations().observe(this, reservationEntities -> {
            if(reservationEntities != null){
                reservations = reservationEntities;
                adapter.setData(reservations);
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void deleteReservationDialog(int position) {
        final ReservationEntity reservation = reservations.get(position);
        LayoutInflater inflater = LayoutInflater.from(this);
        final View view = inflater.inflate(R.layout.row_delete_item, null);

        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.setTitle(R.string.alert_reservationActivity_deleteTitle);
        alertDialog.setCancelable(false);
        final TextView tvDeleteMessage = view.findViewById(R.id.tv_delete_item);
        tvDeleteMessage.setText(R.string.alert_reservationActivity_deleteMessage);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, getString(R.string.alert_playersActivity_btnPositive), (dialog, which) -> {
            Toast toast = Toast.makeText(this, getString(R.string.toast_reservationActivity_delete), Toast.LENGTH_LONG);
            listViewModel.deleteReservation(reservation, new OnAsyncEventListener() {
                @Override
                public void onSuccess() {
                    //log success
                }

                @Override
                public void onFailure(Exception e) {
                    //log failure
                }
            });
            toast.show();
        });

        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, getString(R.string.alert_playersActivity_btnNegative), (dialog, which) -> alertDialog.dismiss());
        alertDialog.setView(view);
        alertDialog.show();
    }
}
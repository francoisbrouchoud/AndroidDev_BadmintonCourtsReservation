package com.example.androiddev_badmintoncourtreservation.viewmodel.reservation;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.example.androiddev_badmintoncourtreservation.BaseApp;
import com.example.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;
import com.example.androiddev_badmintoncourtreservation.database.repository.ReservationRepository;
import com.example.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;


public class ReservationViewModel extends AndroidViewModel {

    private Application application;
    private ReservationRepository repository;
    private final MediatorLiveData<ReservationEntity> observableReservation;

    public ReservationViewModel(@NonNull Application application, final long reservationId, ReservationRepository reservationRepository ) {
        super(application);

        this.application = application;
        repository = reservationRepository;

        observableReservation = new MediatorLiveData<>();
        observableReservation.setValue(null);

        LiveData<ReservationEntity> reservation;
        reservation = repository.getReservation(reservationId, application);
        observableReservation.addSource(reservation, observableReservation::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{
        @NonNull
        private final Application application;
        private final long reservationId;
        private final ReservationRepository repository;

        public Factory(@NonNull Application application, long reservationId) {
            this.application = application;
            this.reservationId = reservationId;
            repository = ((BaseApp) application).getReservationRepository();
        }

        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            return (T) new ReservationViewModel(application, reservationId, repository);
        }
    }

    public LiveData<ReservationEntity> getReservation(){
        return observableReservation;
    }

    public void createReservation(ReservationEntity reservation, OnAsyncEventListener callback){
        repository.insert(reservation, callback, application);
    }

    public void updateReservation(ReservationEntity reservation, OnAsyncEventListener callback){
        repository.update(reservation, callback, application);
    }

    public void deleteReservation(ReservationEntity reservation, OnAsyncEventListener callback){
        repository.delete(reservation, callback, application);
    }
}
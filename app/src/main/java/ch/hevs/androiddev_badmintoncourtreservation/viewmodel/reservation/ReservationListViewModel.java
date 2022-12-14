package ch.hevs.androiddev_badmintoncourtreservation.viewmodel.reservation;

import android.app.Application;
import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import ch.hevs.androiddev_badmintoncourtreservation.BaseApp;
import ch.hevs.androiddev_badmintoncourtreservation.database.entity.ReservationEntity;
import ch.hevs.androiddev_badmintoncourtreservation.database.pojo.ReservationWithPlayerAndCourt;
import ch.hevs.androiddev_badmintoncourtreservation.database.repository.CourtRepository;
import ch.hevs.androiddev_badmintoncourtreservation.database.repository.PlayerRepository;
import ch.hevs.androiddev_badmintoncourtreservation.database.repository.ReservationRepository;
import ch.hevs.androiddev_badmintoncourtreservation.util.OnAsyncEventListener;
import java.util.List;

public class ReservationListViewModel extends AndroidViewModel {

    private Application application;
    private ReservationRepository repository;
    private final MediatorLiveData<List<ReservationEntity>> observableReservations;
    private final MediatorLiveData<List<ReservationWithPlayerAndCourt>> observableReservationsPlayerCourt;

    public ReservationListViewModel(@NonNull Application application, ReservationRepository reservationRepository, PlayerRepository playerRepository, CourtRepository courtRepository) {
        super(application);

        this.application = application;
        repository = reservationRepository;

        observableReservations = new MediatorLiveData<>();
        observableReservationsPlayerCourt = new MediatorLiveData<>();
        observableReservations.setValue(null);
        observableReservationsPlayerCourt.setValue(null);

        LiveData<List<ReservationEntity>> reservations = reservationRepository.getReservations(application);
        LiveData<List<ReservationWithPlayerAndCourt>> reservationsPlayerCourt = reservationRepository.getReservationsWithPlayerAndCourt(application);

        observableReservations.addSource(reservations, observableReservations::setValue);
        observableReservationsPlayerCourt.addSource(reservationsPlayerCourt, observableReservationsPlayerCourt::setValue);
    }

    public static class Factory extends ViewModelProvider.NewInstanceFactory{

        @NonNull
        private final Application application;
        private final ReservationRepository reservationRepository;
        private final PlayerRepository playerRepository;
        private final CourtRepository courtRepository;

        public Factory(@NonNull Application application) {
            this.application = application;
            reservationRepository = ((BaseApp) application).getReservationRepository();
            playerRepository = ((BaseApp) application).getPlayerRepository();
            courtRepository = ((BaseApp) application).getCourtRepository();
        }
        @NonNull
        @Override
        public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
            //Create a new view model for the reservation list
            return (T) new ReservationListViewModel(application, reservationRepository, playerRepository, courtRepository);
        }
    }

    /**
     * Get the observable reservations.
     * @return LiveData of the reservations.
     */
    public LiveData<List<ReservationEntity>> getReservations(){
        return observableReservations;
    }

    /**
     * Get the reservations with the player and the court using the POJO class.
     * @return LiveData of reservations with player ant court.
     */
    public LiveData<List<ReservationWithPlayerAndCourt>> getReservationsWithPlayerCourt(){
        return observableReservationsPlayerCourt;
    }

    /**
     * Delete a reservation.
     * @param reservation to delete.
     * @param callback
     */
    public void deleteReservation(ReservationEntity reservation, OnAsyncEventListener callback){
        repository.delete(reservation, callback, application);
    }
}

package model.services;

import model.entities.CarRental;
import model.entities.Invoice;
import java.time.temporal.ChronoUnit;

public class RentalService {

    private final Double pricePerDay;
    private final Double pricePerHour;

    private final TaxService taxService;

    public RentalService(Double pricePerDay, Double pricePerHour, TaxService taxService) {
        this.pricePerDay = pricePerDay;
        this.pricePerHour = pricePerHour;
        this.taxService = taxService;
    }

    public void processInvoice(CarRental carRental) {
        double minutes = ChronoUnit.MINUTES.between(carRental.getStart(), carRental.getFinish());
        double hour = minutes / 60;

        double basicPayment;
        if (hour <= 12.0) {
            basicPayment = Math.ceil(hour) * pricePerHour;
        } else {
            basicPayment = Math.ceil(hour / 24) * pricePerDay;
        }

        double tax = taxService.tax(basicPayment);

        carRental.setInvoice(new Invoice(basicPayment, tax));
    }
}

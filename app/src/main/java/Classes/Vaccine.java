package Classes;

public class Vaccine {
    String VaccineName,DateVaccinated,timeLine,Mode,vaccineKey;

    public Vaccine() {
    }

    public String getVaccineName() {
        return VaccineName;
    }

    public void setVaccineName(String vaccineName) {
        VaccineName = vaccineName;
    }

    public String getDateVaccinated() {
        return DateVaccinated;
    }

    public void setDateVaccinated(String dateVaccinated) {
        DateVaccinated = dateVaccinated;
    }

    public String getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(String timeLine) {
        this.timeLine = timeLine;
    }

    public String getMode() {
        return Mode;
    }

    public void setMode(String mode) {
        Mode = mode;
    }

    public String getVaccineKey() {
        return vaccineKey;
    }

    public void setVaccineKey(String vaccineKey) {
        this.vaccineKey = vaccineKey;
    }
}



package mpocket.project.com.mpocket;

/**
 * Created by abhishek on 12/7/15.
 */
public class PersonalExpensesData {
    private int _id;
    private float _amount;
    private String _purpose;
    private String _date;

    public PersonalExpensesData() {

    }

    public PersonalExpensesData(float amount, String purpose, String date) {
        this._amount = amount;
        this._purpose = purpose;
        this._date = date;
    }

    public int get_id() {
        return this._id;
    }

    public float get_amount() {
        return this._amount;
    }

    public String get_purpose() {
        return this._purpose;
    }

    public String get_date() {
        return this._date;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_amount(float _amount) {
        this._amount = _amount;
    }

    public void set_purpose(String _purpose) {
        this._purpose = _purpose;
    }

    public void set_date(String _date) {
        this._date = _date;
    }
}

package mpocket.project.com.mpocket;

/**
 * Created by abhishek on 13/7/15.
 */
public class DebtData {

    private int _id;
    private float _amount;
    private String _lend_to;
    private String _lend_on;
    private String _return_by;

    public DebtData() {

    }

    public DebtData(float _amount, String _lend_to, String _lend_on, String _return_by) {
        this._amount = _amount;
        this._lend_to = _lend_to;
        this._lend_on = _lend_on;
        this._return_by = _return_by;

    }

    public int get_id() {
        return this._id;
    }

    public float get_amount() {
        return this._amount;
    }

    public String get_lend_to() {
        return this._lend_to;
    }

    public String get_lend_on() {
        return this._lend_on;
    }

    public String get_return_by() {
        return this._return_by;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_amount(float _amount) {
        this._amount = _amount;
    }

    public void set_lend_to(String _lend_to) {
        this._lend_to = _lend_to;
    }

    public void set_lend_on(String _lend_on) {
        this._lend_on = _lend_on;
    }

    public void set_return_by(String _return_by) {
        this._return_by = _return_by;
    }
}

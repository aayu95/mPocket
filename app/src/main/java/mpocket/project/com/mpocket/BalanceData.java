package mpocket.project.com.mpocket;

/**
 * Created by abhishek on 15/7/15.
 */
public class BalanceData {

    private int _id;
    private float _amount;


    public BalanceData() {

    }

    public BalanceData(float _amount) {
        this._amount = _amount;
    }

    public float get_amount() {
        return this._amount;
    }

    public int get_id() {
        return this._id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_amount(float _amount) {
        this._amount = _amount;
    }
}

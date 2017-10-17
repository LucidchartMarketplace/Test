package studyelephant.com.studyelephant.model;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Zhihan on 10/17/2017.
 */

public class College {
    public int id;
    public int ownership;
    public String name;
    public String city;
    public String state;
    public String school_url;
    public int highest_degrees_awarded;
    public double overall_admission_rate;
    public double completion_rate;
    public double annual_cost;
    public double earning;
    public int student_size;
    public int cost_0_30000;
    public int cost_30001_48000;
    public int cost_48001_75000;
    public int cost_75001_110000;
    public int cost_1100001_plus;

    public College(JSONObject college) {
        try {
            if (!college.isNull("id"))
                this.id = college.getInt("id");
            if (!college.isNull("school.ownership"))
                this.ownership = college.getInt("school.ownership");
            if (!college.isNull("school.name"))
                this.name = college.getString("school.name");
            if (!college.isNull("school.school_url"))
                this.school_url = college.getString("school.school_url");
            if (!college.isNull("school.degrees_awarede.highest"))
                this.highest_degrees_awarded = college.getInt("school.degrees_awarded.highest");
            if (!college.isNull("school.city"))
                this.city = college.getString("school.city");
            if (!college.isNull("school.state"))
                this.state = college.getString("school.state");
            if (!college.isNull("2015.completion.completion_rate_4yr_150nt"))
                this.completion_rate = college.getDouble("2015.completion.completion_rate_4yr_150nt");
            if (!college.isNull("2015.admissions.admission_rate.overall"))
                this.overall_admission_rate = college.getDouble("2015.admissions.admission_rate.overall");
            if (!college.isNull("2013.earnings.10_yrs_after_entry.median"))
                this.earning = college.getDouble("2013.earnings.10_yrs_after_entry.median");
            if (!college.isNull("2015.student.size"))
                this.student_size = college.getInt("2015.student.size");
            switch (this.ownership) {
                case 1:
                    if (!college.isNull("2015.cost.avg_net_price.public"))
                        this.annual_cost = college.getDouble("2015.cost.avg_net_price.public");
                    if (!college.isNull("2015.cost.net_price.public.by_income_level.0-30000"))
                        this.cost_0_30000 = college.getInt("2015.cost.net_price.public.by_income_level.0-30000");
                    if (!college.isNull("2015.cost.net_price.public.by_income_level.30001-48000"))
                        this.cost_30001_48000 = college.getInt("2015.cost.net_price.public.by_income_level.30001-48000");
                    if (!college.isNull("2015.cost.net_price.public.by_income_level.48001-75000"))
                        this.cost_48001_75000 = college.getInt("2015.cost.net_price.public.by_income_level.48001-75000");
                    if (!college.isNull("2015.cost.net_price.public.by_income_level.75001-110000"))
                        this.cost_75001_110000 = college.getInt("2015.cost.net_price.public.by_income_level.75001-110000");
                    if (!college.isNull("2015.cost.net_price.public.by_income_level.110001-plus"))
                        this.cost_1100001_plus = college.getInt("2015.cost.net_price.public.by_income_level.110001-plus");
                    break;
                case 2:
                case 3:
                    if (!college.isNull("2015.cost.avg_net_price.private"))
                        this.annual_cost = college.getDouble("2015.cost.avg_net_price.private");
                    if (!college.isNull("2015.cost.net_price.private.by_income_level.0-30000"))
                        this.cost_0_30000 = college.getInt("2015.cost.net_price.private.by_income_level.0-30000");
                    if (!college.isNull("2015.cost.net_price.private.by_income_level.30001-48000"))
                        this.cost_30001_48000 = college.getInt("2015.cost.net_price.private.by_income_level.30001-48000");
                    if (!college.isNull("2015.cost.net_price.private.by_income_level.48001-75000"))
                        this.cost_48001_75000 = college.getInt("2015.cost.net_price.private.by_income_level.48001-75000");
                    if (!college.isNull("2015.cost.net_price.private.by_income_level.75001-110000"))
                        this.cost_75001_110000 = college.getInt("2015.cost.net_price.private.by_income_level.75001-110000");
                    if (!college.isNull("2015.cost.net_price.private.by_income_level.110001-plus"))
                        this.cost_1100001_plus = college.getInt("2015.cost.net_price.private.by_income_level.110001-plus");
                    break;
            }
        }
        catch (JSONException e) {
            e.getMessage();
            Log.d(e.getMessage().toString(),"");
        }
    }
}

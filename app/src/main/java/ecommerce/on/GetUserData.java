package ecommerce.on;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetUserData {

    @SerializedName("status")
    @Expose
    public Boolean status;
    @SerializedName("message")
    @Expose
    public String message;
    @SerializedName("UserData")
    @Expose
    public List<GetUserDataResponse> userData;

    public class GetUserDataResponse {
        @SerializedName("userid")
        @Expose
        public String userid;
        @SerializedName("first_name")
        @Expose
        public String firstName;
        @SerializedName("last_name")
        @Expose
        public String lastName;
        @SerializedName("email")
        @Expose
        public String email;
        @SerializedName("contact")
        @Expose
        public String contact;
        @SerializedName("gender")
        @Expose
        public String gender;
    }
}

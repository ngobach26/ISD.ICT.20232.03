package utils;

public class AdminUtils {
    public static String getUserTypeString(int userType) {
        switch (userType) {
            case 0:
                return "User";
            case 1:
                return "Manager";
            case 2:
                return "Admin";
            default:
                return "Unknown";
        }
    }

    public static String getUserStatusString(int userStatus) {
        switch (userStatus) {
            case 0:
                return "Active";
            case 1:
                return "Block";
            default:
                return "Unknown";
        }
    }
    public static int getUserTypeValue(String userType) {
        switch (userType) {
            case "User": return 0;
            case "Manager": return 1;
            case "Admin": return 2;
            default: return 0;
        }
    }

    public static int getUserStatusValue(String status) {
        switch (status) {
            case "Active": return 0;
            case "Block": return 1;
            default: return 0;
        }
    }
}

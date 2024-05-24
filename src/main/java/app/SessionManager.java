package app;

import models.User;

    public class SessionManager {
        private static User user;

        //
        public static void setUser(User newUser){
            user = newUser;
        }
        public static User getUser(){
            return user;
        }
    }


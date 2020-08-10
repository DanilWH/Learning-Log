package com.example.LearningLog.controllers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import com.example.LearningLog.models.Accesses;
import com.example.LearningLog.models.Entry;
import com.example.LearningLog.models.Topic;
import com.example.LearningLog.models.User;
import com.example.LearningLog.repos.UserRepo;

public interface CommonOperationsForControllers {
    
    public static String fieldRequired = "This field is required.";
    public static int passwordLength = 8;
    
    public static void checkTopicOwner(Topic topic, User current_user) {
        /*** Checks if the current user is the owner of the topic. ***/
        
        String topic_owner = topic.getOwner().getUsername();
        
        if (topic_owner.equals(current_user.getUsername()) != true)
            throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
    
    public static void checkTopicAccessabilityAndTopicOwner(Topic topic, User current_user) {
        /***
         * Checks if the topic has private access and
         * if the current user is the owner of the topic.
        ***/
        
        if (topic.getAccess() == Accesses.PRIVATE)
            checkTopicOwner(topic, current_user);
    }
    
    public static String isUsernameValid(String username, UserRepo userRepo) {
        /*** 
         * Checks if the username is valid.
         * If it's not then it returns a message what's incorrect.
        ***/
        
        User userFromDB = userRepo.findByUsername(username);

        if (userFromDB != null)
            return "The user already exsists!";
        
        char[] username_chars = username.toCharArray();
        for (int i = 0, len = username_chars.length; i < len; i++) {
            // if the current symbol is in the range of numbers by ascii. 
            boolean numerics = username_chars[i] > 47 && username_chars[i] < 58;
            // if the current symbol is in the range of upper letters by ascii.
            boolean upper_letters = username_chars[i] > 64 && username_chars[i] < 91;
            // if the current symbol is in the range of lower letters by ascii.
            boolean lower_letters = (username_chars[i] > 96 && username_chars[i] < 123);
            // if the current symbol is one of the special symbols.
            boolean special_symbols = username_chars[i] == '.' || username_chars[i] == '+' ||
                                      username_chars[i] == '-' || username_chars[i] == '_' ||
                                      username_chars[i] == '@';
            // is the current symbol valid?
            boolean valid_symbol = numerics || upper_letters || lower_letters || special_symbols; 
            
            // if the current symbol isn't valid then ...
            if (!valid_symbol)
                // ... return the appropriate message.
                return "Enter a valid username. This value may contain only letters,"
                        + "numbers, and @/./+/-/_ characters.";
        }
        
        // return null as a result if the username is valid.
        return null;
    }
    
    public static String isPasswordValid(String password, String password_confirm) {
        /*** 
         * Checks if the password is valid.
         * If it's not then it returns a message what's incorrect.
        ***/
        
        if (password.equals(password_confirm) == false)
            return "Your passwords didn't match. Please try again.";
        if (password.length() < passwordLength || password_confirm.length() < passwordLength)
            return "This password is too short. It must contain at least "
            + passwordLength + " characters.";
        
        boolean passwordContainsNumericsOnly = true;
        char[] password_chars = password.toCharArray();
        for (int i = 0, len = password_chars.length; i < len; i++) {
            boolean numerics = password_chars[i] > 47 && password_chars[i] < 58;
            
            if (!numerics) {
                passwordContainsNumericsOnly = false;
                break;
            }
        }
        if (passwordContainsNumericsOnly) return "This password is entirely numeric.";
        
        // return null as a result if the password is valid.
        return null;
    }
    
    public static void uploadFilesIfExist(
            Entry entry,
            List<MultipartFile> files,
            String uploadPath
    ) throws IOException {
        /*** 
         * Uploads the user's chosen files
         * but if the user didn't choose any file
         * then this function doesn't do anything as well.
        ***/
        
        /*
         * Because the forms like <input type="file" multiple /> return at least
         * 1 object even if it's empty we have to check if the first element
         * contains an empty value, if so then that means the user didn't select any file
         * so we make sure if the user selected at least one file.
         */
        if (files != null && files.get(0).isEmpty()) return;
        
        // but if the user selected at least one file then we:
        // make the directory if it doesn't exist.
        File uploadDir = new File(uploadPath);

        if (!uploadDir.exists())
            uploadDir.mkdirs();
        
        // go through the list of files.
        for (MultipartFile file : files) {
            // create a unique name for the current file.
            String uuidFilename = UUID.randomUUID().toString();
            String resultFilename = uuidFilename + "." + file.getOriginalFilename();
            
            // store store the current file on the server in the path we
            // pointed in application.properties.
            file.transferTo(new File(uploadPath + resultFilename));
            
            // store the name of the current file to the list of the new entry.
            entry.getFilenames().add(resultFilename);
        }
    }
    
    public static void deleteFilesFromServerIfExist(
            Entry entry, String[] filesList, String uploadPath
    ) {
        /*** 
         * Removes the selected files from
         * the entry entity in the repository
         * and form the server directory.
        ***/
        
        // we don't need to do anything if the current entry doesn't contain files.
        if (entry != null && entry.getFilenames().isEmpty()) return;
        
        // but if the entry contain at least one file then we have to delete it.
        for (String filename : filesList) {
            // remove the files from the entry in the repository.
            entry.getFilenames().remove(filename);
        
            // remove the files from the server directory.
            File removingFile = new File(uploadPath + filename);
            removingFile.delete();
        }
    }
    
    public static void deleteFilesFromServerIfExist(Entry entry, String uploadPath) {
        /*** 
         * This is a prototype of the function
         * deleteFilesFromServerIfExist(Entry entry, String[] filesList, String uploadPath)
         * but a little bit more readable.
         */
        String[] filesList = entry.getFilenames().toArray(new String[0]);
        deleteFilesFromServerIfExist(entry, filesList, uploadPath);
    }
    
    public static String getTimeAgo(LocalDateTime localDateTime) {
        ChronoUnit[] chronoUnits = {
                ChronoUnit.SECONDS, ChronoUnit.MINUTES, ChronoUnit.HOURS,
                ChronoUnit.DAYS, ChronoUnit.WEEKS, ChronoUnit.MONTHS, ChronoUnit.YEARS
        };
        String[] stringUnits = {"second", "minute", "hour", "day", "week", "month", "year"};
        
        int count = 0;
        do
            count++;
        while (count < chronoUnits.length &&
               chronoUnits[count].between(localDateTime, LocalDateTime.now()) != 0);
        
        return chronoUnits[count - 1].between(localDateTime, LocalDateTime.now()) +
               " " + stringUnits[count - 1] +
               " ago";
    }
}

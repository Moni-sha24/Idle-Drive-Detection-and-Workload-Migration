package com.idledrive.idledrive;

import com.idledrive.idledrive.model.User;
import com.idledrive.idledrive.model.LogEntry;
import com.idledrive.idledrive.repository.UserRepository;
import com.idledrive.idledrive.repository.LogEntryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.net.URLDecoder;
import java.time.LocalDateTime;
import java.util.*;

@CrossOrigin(origins = "*")
@RestController
public class DriveController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private LogEntryRepository logEntryRepository;

    // Example: currently logged-in user
    private final String CURRENT_USER = "Moni";

    // Test endpoint
    @GetMapping("/test")
    public String test(){
        return "Backend is working";
    }

    // Endpoint: list all drives
    @GetMapping("/api/drives")
    public List<Drive> getDrives() {

        logAction("Viewed all drives");

        List<Drive> drivesList = new ArrayList<>();
        File[] roots = File.listRoots();

        if (roots != null) {
            for (File root : roots) {

                long total = root.getTotalSpace();
                long free = root.getFreeSpace();
                long used = total - free;

                double usagePercent = 0;
                if (total > 0) {
                    usagePercent = ((double) used / total) * 100;
                }

                String status;
                if (usagePercent < 30)
                    status = "Idle";
                else if (usagePercent < 70)
                    status = "Working";
                else
                    status = "Overloaded";

                drivesList.add(new Drive(
                        root.getAbsolutePath(),
                        total,
                        free,
                        usagePercent,
                        status
                ));
            }
        }

        return drivesList;
    }

    // Endpoint: list files in a drive
    @GetMapping("/api/drives/{driveName}/files")
    public List<Map<String, Object>> getFilesInDrive(@PathVariable String driveName) {

        logAction("Viewed files in drive: " + driveName);

        try {
            driveName = URLDecoder.decode(driveName, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Map<String,Object>> filesList = new ArrayList<>();
        File drive = new File(driveName);

        if(drive.exists() && drive.isDirectory()){
            File[] files = drive.listFiles();

            if(files != null){
                for(File f : files){

                    Map<String,Object> fileMap = new HashMap<>();
                    fileMap.put("name", f.getName());
                    fileMap.put("accessible", f.canRead());

                    filesList.add(fileMap);
                }
            }
        }

        return filesList;
    }

    // Helper method to log actions
    private void logAction(String action){

        User user = userRepository.findByUsername(CURRENT_USER);

        if(user == null){
            user = new User();
            user.setUsername(CURRENT_USER);
            userRepository.save(user);
        }

        LogEntry log = new LogEntry();
        log.setUser(user);
        log.setAction(action);
        log.setTimestamp(LocalDateTime.now());

        logEntryRepository.save(log);
    }
}
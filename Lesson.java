//Batagianni Lamprini-Theodosia 3150115
public class Lesson {
    private String code;
    private String lessonName;
    private String class_s;
    private int numberofhours;
    private int availablehours;
    private boolean active;
    
    public int getAvailableHours(){
        return availablehours;
    }
    
    public void upL(){
        availablehours = numberofhours;
        active = true;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public void setLessonName(String lessonName) {
        this.lessonName = lessonName;
    }
    
    public void setClass(String classs) {
        if (classs.equals("A") || classs.equals("B") || classs.equals("C")) class_s = classs;
    }
    
    public void setNumberOfHours(int numofhours) {
        if (numofhours >= 1 && numofhours <= 7) numberofhours = numofhours;
    }
    
    public String getCode(){
        return code;
    }
    
    public String getLessonName(){
        return lessonName;
    }
    
    public String getClassABC(){
        return class_s;
    }
    
    public int getNumberOfHours(){
        return numberofhours;
    }
    
    public boolean Active(){
        return active;
    }
    
    public void reduce(){
        availablehours--;
        active = false;
    }
    
    public void activate(){
        active = true;
    }
}
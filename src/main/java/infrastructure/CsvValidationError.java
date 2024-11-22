package infrastructure;

// This error will be thrown if student csv data failed validation
public class CsvValidationError extends Exception{
    public CsvValidationError(String message){
        super(message);
    }

    public CsvValidationError(){
        super("Invalid student data format");
    }
}


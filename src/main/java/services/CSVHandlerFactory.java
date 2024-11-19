package services;

import infrastructure.DefaultCSVHandler;
import infrastructure.ICSVHandler;

public class CSVHandlerFactory {
    public static ICSVHandler createCSVHandler(CSVHandlerType handlerType){
        ICSVHandler csvHandler = null;

       switch (handlerType) {
           case CSV_API, DEFAULT -> csvHandler = new DefaultCSVHandler();
       }

        return csvHandler;
    }
}

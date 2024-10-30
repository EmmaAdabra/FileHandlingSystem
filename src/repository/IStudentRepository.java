package repository;

import infrastructure.ICSVHandler;
import util.Response;

public interface IStudentRepository {
    Response loadStudentCSV();
}

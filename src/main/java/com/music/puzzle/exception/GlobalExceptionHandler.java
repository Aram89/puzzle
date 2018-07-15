package com.music.puzzle.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class GlobalExceptionHandler {

    private  static Logger logger = LogManager.getLogger(GlobalExceptionHandler.class);


    /**
     * Returning INTERNAL_SERVER_ERROR
     * for all unhandled exceptions
     *
     * @param e Exception.
     * @return
     */
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity exception(Exception e) {
        logger.error("Error: ", e);
        return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
    }

    /**
     * Returning INTERNAL_SERVER_ERROR
     * for all unhandled exceptions
     *
     * @param se sql Exception.
     * @return
     */
    @ExceptionHandler(DataAccessException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorCode sqlException(Exception se) {
        logger.error("Error: ", se);
        return new ErrorCode("wrong id");
    }


    /**
     * Returning bad request status
     * and error string for user exceptions
     * @param ae Exception.
     * @return error string in json.
     */

    @ExceptionHandler(AppException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public ErrorCode userException (AppException ae) {
        logger.error("Error: ", ae);
        return new ErrorCode(ae.getMessage());
    }
}
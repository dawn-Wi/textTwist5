package com.example.texttwist5;

public class Result<T>{
    private Result(){}

    @Override
    public String toString(){
        if(this instanceof Result.Success){
            Success success = (Success) this;
            return "Success[data=" + success.getData().toString()+"]";
        }
        else if(this instanceof Result.Error){
            Error error = (Error) this;
            return "Error[exception=" + error.getError().toString()+"]";
        }
        return "";
    }

    public final static class Success<T> extends Result{
        private T data;
        public Success(T data) {this.data = data;}
        public T getData(){return this.data;}
    }

    public final static class Error extends Result{
        private Exception error;
        public Error(Exception error) {this.error = error;}
        public Exception getError(){return this.error;}
    }

}

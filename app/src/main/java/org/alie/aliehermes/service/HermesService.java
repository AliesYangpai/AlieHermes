package org.alie.aliehermes.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import org.alie.aliehermes.EventBusService;
import org.alie.aliehermes.Request;
import org.alie.aliehermes.Response;

public class HermesService extends Service {
    public HermesService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
       return mBinder;
    }


   private EventBusService.Stub mBinder =  new EventBusService.Stub(){
       @Override
       public Response send(Request request) throws RemoteException {
           return null;
       }
   };
}

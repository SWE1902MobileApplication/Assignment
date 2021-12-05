//TODO remove this shit

package com.example.assignment;

import static java.lang.Thread.sleep;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

public class DatabaseHelper {
    private FirebaseFirestore db;
    private Map<String, Object> docContent;
    //TODO remove
    public void test(){
        initDatabase();
        /*Map<String, Object> docContent = getDocContent("test", "testdoc");
        Log.e("a",docContent.get("testdockey").toString());
        for(Object s : getDocContent("test", "testdoc").values()){
            Log.e("AAA","AAA");
            Log.e("TAG", (String)s);
        }*/
        /*
        db.collection("test").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                Log.e("doc[0].get",task.getResult().getDocuments().get(0).get("appKey1", Integer.class).toString());
            }
        });

        db.collection("test").document("testdoc").get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                Log.e("getdoc", task.getResult().get("testdockey").toString());
            }
        });*/
        //db.collection("test").document("testdoc").update("testdockey","updatedfromappagain");
        /*HashMap<String, Integer> m = new HashMap<>();
        m.put("appKey1", 1);
        m.put("appKey2", 2);
        m.put("appKey3", 3);*/
        //createNewDocument("test", "newDocFromApp", m);
    }

    public synchronized <T> boolean createNewDocument(String collectionName, String newDocName, Map<String, T> documentData){
        initDatabase();
        //TODO exceptions and multithread
        return db.collection(collectionName).document(newDocName).set(documentData).isSuccessful();
    }

    public synchronized Map<String, Object> getDocContent(String collectionName, String docName){
        Log.e("Flow", "start");
        initDatabase();
        Log.e("Flow", "inited");
        Runnable t = new Runnable(){
            @Override
            public void run() {
                db.collection(collectionName).document(docName).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        Log.e("Flow", "oncomplete");
                        docContent = new HashMap<>();
                        Log.e("Flow", "new map");
                        for(String key : task.getResult().getData().keySet()){
                            docContent.put(key, task.getResult().getData().get(key));
                            Log.e(key, task.getResult().getData().get(key).toString());
                        }
                        Log.e("Flow", "end for");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.e("Flow", "failed");
                        docContent = new HashMap<>();
                    }
                });
            }
        };
        t.run();

        while (docContent == null){
            Log.e("Flow", "in while");
            try{

                Log.e("Flow", "wait");
                wait(500);
            }catch(InterruptedException e){

                Log.e("Flow", "catch");
            }
        }
        Log.e("Flow", "before return");
        return docContent;
    }

    private void initDatabase(){
        db = FirebaseFirestore.getInstance();
    }
}

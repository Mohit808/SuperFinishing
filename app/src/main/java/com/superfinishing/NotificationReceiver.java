package com.superfinishing;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;
import androidx.legacy.content.WakefulBroadcastReceiver;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Objects;

public class NotificationReceiver extends WakefulBroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String data = intent.getExtras().getString("data");
        System.out.println("Notification Received,..,.,. "+data);

//        WorkSpace.initDb(context);
        try {
            final JSONObject jsonObject = new JSONObject(data);
            final HashMap<String, String> hashMap = new Gson().fromJson(jsonObject.toString(), HashMap.class);
            hashMap.put("userKey",hashMap.get("sender"));

//            String type = jsonObject.getString("type");
//            ContentValues contentValue = new ContentValues();
//            contentValue.put("id",jsonObject.getString("pushKey"));
//            long ins = WorkSpace.localRef.insert("isInTable", null, contentValue);
//            System.out.println("Basic insss "+ins);
//            if (ins>-1){
//                if (type.equals("fiendShip")){
                    showNotification(context,hashMap.get("title"),hashMap.get("desc"),hashMap.get("title"),new Intent(context, MainActivity.class));
//                    if (hashMap.get("friendShipStatus").equals("request")){
//                        friendInsert(hashMap);
//                    }
//                }
//                if (type.equals("chats")){
//                    showNotification(context,hashMap.get("userName"),hashMap.get("msg"),hashMap.get("userImage"),new Intent(context, MainActivity.class));
//                    chatInsert(hashMap, "chats", hashMap.get("sender"));
//
//                    Cursor cursor = localRef.rawQuery("select * from friendShip where userKey=?", new String[]{hashMap.get("userKey")});
//                    if (cursor.moveToNext()){
//                        String user = cursor.getString(0);
//                    }else {
//                        hashMap.put("friendShipStatus","friend");
//                        friendInsert(hashMap);
//                    }
//                }

//                if (type.equals("groupChats")){
//                    if (!hashMap.get("sender").equals(currentUser)){
//                        showNotification(context,hashMap.get("groupName"),hashMap.get("msg"),hashMap.get("groupImage"),new Intent(context, MainActivity.class));
//                        groupChatInsert(hashMap);
//
//                        if (hashMap.get("isCreated")!=null && hashMap.get("isCreated").equals("true")){
//                            String groupDetails = hashMap.get("groupDetails");
//                            String groupMembers = hashMap.get("groupMembers");
//                            HashMap<String, String> hashMapGroupDetails = new Gson().fromJson(groupDetails, HashMap.class);
//                            groupInsert(hashMapGroupDetails);
//
//                            ArrayList<HashMap<String,String>> listMembers = new Gson().fromJson(groupMembers, ArrayList.class);
//                            for (int i=0;i<=listMembers.size()-1;i++){
//                                HashMap<String, String> hashMapGroupMembers = new Gson().fromJson(new Gson().toJson(listMembers.get(i)), HashMap.class);
//                                memberInsert(hashMapGroupMembers.get("pushKey"),hashMap.get("groupKey"),hashMapGroupMembers.get("userKey"),hashMapGroupMembers.get("userName"),hashMapGroupMembers.get("userImage"),hashMapGroupMembers.get("power"),hashMap.get("time"));
//                            }
//                            if (hashMap.get("groupKey")!=null){
//                                FirebaseMessaging.getInstance().subscribeToTopic(Objects.requireNonNull(hashMap.get("groupKey")));
//                            }
//                        }
//                        if (hashMap.get("groupPowerChanged")!=null && hashMap.get("groupPowerChanged").equals("yes")){
//                            if (hashMap.get("changedPower").equals("Leader")){
//                                updatePower(hashMap.get("groupKey"),hashMap.get("changedUserKey"),"Leader");
//                                updatePower(hashMap.get("groupKey"),hashMap.get("sender"),"Co-Leader");
//                            }
//                            if (hashMap.get("changedPower").equals("Co-Leader")){
//                                updatePower(hashMap.get("groupKey"),hashMap.get("changedUserKey"),"Co-Leader");
//                            }
//                            if (hashMap.get("changedPower").equals("Member")){
//                                updatePower(hashMap.get("groupKey"),hashMap.get("changedUserKey"),"Member");
//                            }
//                            if (hashMap.get("changedPower").equals("Kick-out")){
//                                FirebaseMessaging.getInstance().unsubscribeFromTopic(hashMap.get("groupKey"));
//                                localRef.delete("members","groupKey=? and userKey=?",new String[]{hashMap.get("groupKey"),hashMap.get("userKey")});
//                            }
//                        }
//                    }
//                }
//                Intent intent1=new Intent("broadcast");
//                intent1.putExtra("data",hashMap);
//                context.sendBroadcast(intent1);
//            }

//                chatMessageReceivedFunctionality(type,hashMap);
//
//                if (!jsonObject.getString("sender").equals(currentUser) && type.equals("groupChats")){
//                    System.out.println("in notification groupChat..... "+jsonObject.getString("sender"));
//                    if (!jsonObject.getString("sender").equals(currentUser)){
//                        WorkSpace.chatInsert(hashMap,"groupChats","groupKey");
//                        String containsInChatString = containsChatList("groupChatLists","groupKey",hashMap.get("groupKey"));
//                        insUpdChatList(containsInChatString,hashMap,"groupChatLists","groupKey");
//                        showNotification(context,jsonObject.getString("groupName"),jsonObject.getString("msg"),"image",new Intent(context, FriendsActivity.class));
//
//                        if (hashMap.get("subType")!=null && hashMap.get("subType").equals("CreateGroup")){
//                            WorkSpace.groupInsert(hashMap.get("groupKey"),hashMap.get("groupName"),hashMap.get("groupImage"),hashMap.get("time"));
//                            addMemberFromSplt(hashMap);
////
//                        }
//
//                        if (hashMap.get("subType")!=null && hashMap.get("subType").equals("AddMembers")){
//                            long insss = WorkSpace.groupInsert(hashMap.get("groupKey"), hashMap.get("groupName"), hashMap.get("groupImage"), hashMap.get("time"));
//                            if (insss>-1){
//                                FirebaseMessaging.getInstance().subscribeToTopic(hashMap.get("groupKey"));//new Added
//                                WorkSpace.memberInsert(hashMap.get("pushKey"),hashMap.get("groupKey"),hashMap.get("sender"),hashMap.get("name"),hashMap.get("image"),hashMap.get("senderPower"),hashMap.get("time"));
//                                db.collection("members").whereEqualTo("groupKey",hashMap.get("groupKey")).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                                    @Override
//                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                                        if (task.isSuccessful()){
//                                            localRef.delete("members","groupKey=?",new String[]{hashMap.get("groupKey")});
//                                            for (final QueryDocumentSnapshot snapshot:task.getResult()){
//                                                db.collection("users").document(snapshot.getString("userKey")).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                                    @Override
//                                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                                        if (task.isSuccessful()){
//                                                            WorkSpace.memberInsert(snapshot.getString("pushKey"),snapshot.getString("groupKey"),snapshot.getString("userKey"),task.getResult().getString("name"),task.getResult().getString("image"),snapshot.getString("power"),snapshot.getString("time"));
//                                                        }
//                                                    }
//                                                });
//                                            }
//                                        }
//                                    }
//                                });
//                            }else {
//                                //already group
//                                addMemberFromSplt(hashMap);
//                            }
//                        }
//
//
//                        if (hashMap.get("subType")!=null && hashMap.get("subType").equals("powerChange")){
//                            System.out.println("inside power change,..,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,., ");
//                            if (hashMap.get("power").equals("Co-Leader") || hashMap.get("power").equals("Member") || hashMap.get("power").equals("Leader")){
//                                ContentValues contentValues=new ContentValues();
//                                contentValues.put("power",hashMap.get("power"));
//                                System.out.println("inside power change,..,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.,.before Update,.,, , "+hashMap.get("groupKey")+"  "+hashMap.get("memberKey"));
//                                localRef.update("members",contentValues,"groupKey=? and userKey=?",new String[]{hashMap.get("groupKey"),hashMap.get("memberKey")});
//                                if (hashMap.get("power").equals("Leader")){
//                                    contentValues.put("power","Co-Leader");
//                                    localRef.update("members",contentValues,"groupKey=? and userKey=?",new String[]{hashMap.get("groupKey"),hashMap.get("sender")});
//                                }
//                            }
//                            if (hashMap.get("power").equals("Kick-Out")){
//                                localRef.delete("members","groupKey=? and userKey=?",new String[]{hashMap.get("groupKey"),hashMap.get("memberKey")});
//                                if (jsonObject.getString("memberKey").equals(currentUser)){
//                                    FirebaseMessaging.getInstance().unsubscribeFromTopic(jsonObject.getString("groupKey"));
//                                }
//                            }
//                        }
//                    }
//                }
//
//
//
//
//
//
//                if (!jsonObject.getString("sender").equals(currentUser)){
//                    if (!type.equals("groupChats")){
//                        showNotification(context,jsonObject.getString("name"),jsonObject.getString("msg"),"image",new Intent(context, FriendsActivity.class));
//                    }
//                    Intent intentx = new Intent("channel");
//                    intentx.putExtra("json", hashMap);
//                    context.sendBroadcast(intentx);
//                }
//
//            }
//
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
//
//
//    private void addMemberFromSplt(HashMap<String, String> hashMap){
//        String[] spltPush = hashMap.get("memberPushKey").split(",");
//        String[] spltMemberKey = hashMap.get("memberKey").split(",");
//        String[] spltMemberName = hashMap.get("memberName").split(",");
//        String[] spltMemberImage = hashMap.get("memberImage").split(",");
//        String[] spltMemberPower = hashMap.get("power").split(",");
//        for (int i=0;i<=spltPush.length-1;i++){
//            if (spltMemberKey[i].equals(currentUser)){
//                FirebaseMessaging.getInstance().subscribeToTopic(hashMap.get("groupKey"));
//            }
//            WorkSpace.memberInsert(spltPush[i],hashMap.get("groupKey"),spltMemberKey[i],spltMemberName[i],spltMemberImage[i],spltMemberPower[i],hashMap.get("time"));
//        }
//    }

    public static void showNotification(Context context,String title, String description, String imageUrl, Intent intent){

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        createChannel(context);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT);
        Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(context,"channelID")
                .setSmallIcon(R.drawable.ic_baseline_account_circle_24)
                .setContentTitle(title)
                .setContentText(description)
                .setAutoCancel(true)
                .setSound(defaultSoundUri)
//                .setStyle(new NotificationCompat.MessagingStyle(senderName))
                .setContentIntent(pendingIntent);

        Date currentTime = Calendar.getInstance().getTime();
        long time = currentTime.getTime();

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        int notificationId = (int) time;
        notificationManager.notify(notificationId, notificationBuilder.build());
    }
    public static void createChannel(Context context){
        if (Build.VERSION.SDK_INT < 26) {
            return;
        }

        NotificationManager notificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        NotificationChannel channel = new NotificationChannel("channelID","name", NotificationManager.IMPORTANCE_HIGH);
        channel.setDescription("Description");
        channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
        notificationManager.createNotificationChannel(channel);

    }


}

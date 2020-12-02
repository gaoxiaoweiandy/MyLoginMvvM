package com.example.myloginmvvm.view;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;

import com.example.myloginmvvm.MyApplication;
import com.example.myloginmvvm.R;
import com.example.myloginmvvm.bean.MyConstant;
import com.example.myloginmvvm.bean.User;
import com.example.myloginmvvm.databinding.ActivityUserInfoBinding;
import com.example.myloginmvvm.utils.FileUtil;
import com.example.myloginmvvm.utils.MyLogCat;
import com.example.myloginmvvm.utils.RxjavaPermissionUtil;
import com.example.myloginmvvm.view.selfview.SelectPicPopupWindow;
import com.example.myloginmvvm.vm.UserInfoViewModel;
import com.tbruyelle.rxpermissions2.Permission;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import io.reactivex.functions.Consumer;

public class UserInfoActivity extends BaseActivity<UserInfoViewModel, ActivityUserInfoBinding> implements View.OnClickListener {
    private static final int REQUESTCODE_CAMERA = 1;
    private static final int REQUESTCODE_PICK = 0;
    private static final int REQUESTCODE_CUTTING = 2;
    private static final String TAG = "UserInfoActivity";
    private Bitmap bitmap;
    private String mNickname;
    private SelectPicPopupWindow menuWindow;
    private static final String IMAGE_FILE_NAME = "takePhotoUser.data";
    private String protectorPicFilePath;
    private Uri mTempFileUri;


    /**
     * 上传头像与姓名
     *
     * @param file
     * @param name
     */

    public void postAvatar(File file, String name) {
        User user = new User(this);
        String token = user.getUserToken();
        mViewModel.postPoundList(name,file,token);
    }


    /**
     * 上传姓名
     *
     * @param name: 姓名
     */
    public void postName(String name) {

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        rootFile = new File(MyConstant.PIC_PATH);
        if (!rootFile.exists()) {
            rootFile.mkdirs();
        }
        initView();


    }

    @Override
    int getContentViewId() {
        return R.layout.activity_user_info;
    }

    @Override
    protected void addLifeCycleObserver() {

    }

    private void initView() {
        setToolBar(mDataBinding.toolbar);
        mDataBinding.rlAvatar.setOnClickListener(UserInfoActivity.this);
        mDataBinding.eiAvatar.setOnClickListener(UserInfoActivity.this);
        mDataBinding.btRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //在这里调用接口上传图片
                Log.i(TAG, "filePath=" + protectorPicFilePath);
                String name = mDataBinding.edtNickname.getText().toString();

                if (TextUtils.isEmpty(name)) {
                    Toast.makeText(UserInfoActivity.this, "姓名不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                File file = null;
                if (!TextUtils.isEmpty(protectorPicFilePath)) {
                    file = new File(protectorPicFilePath);
                }

                User user = new User(UserInfoActivity.this);

                //缓存的头像路径为空，则直接返回
                if (file == null) {
                    postName(name);
                    return;
                } else {
                    Log.i(TAG,"点击了保存按钮");
                    postAvatar(file, name);
                }

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bitmap != null)
            bitmap.recycle();
    }


    private View.OnClickListener itemsOnClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            menuWindow.dismiss();
            switch (v.getId()) {
                case R.id.takePhotoBtn:
                    final String[] permissionRequest = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    RxPermissions permissions = new RxPermissions(UserInfoActivity.this);
                    permissions.setLogging(true);
                    permissions.requestEachCombined(permissionRequest)
                            .subscribe(new Consumer<Permission>() {
                                @Override
                                public void accept(Permission permission) throws Exception {
                                    if (permission.granted) {//同意后调用
                                        MyLogCat.i(TAG, permission.name + " is granted");
                                        takePhoto();
                                    } else if (permission.shouldShowRequestPermissionRationale) {//禁止，但没有选择“以后不再询问”，以后申请权限，会继续弹出提示
                                        MyLogCat.i(TAG, permission.name + " is refused and will alarm next");
                                    } else {//禁止，但选择“以后不再询问”，以后申请权限，不会继续弹出提示
                                        MyLogCat.i(TAG, permission.name + " is refused and need manual set");
                                        RxjavaPermissionUtil.getInstance().alertOpenNavPermission(UserInfoActivity.this, permissionRequest);
                                    }
                                }
                            });
                    break;
                case R.id.pickPhotoBtn:
                    final String[] permissionPickRequest = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                    RxPermissions permissionsPick = new RxPermissions(UserInfoActivity.this);
                    permissionsPick.setLogging(true);
                    permissionsPick.requestEachCombined(permissionPickRequest)
                            .subscribe(new Consumer<Permission>() {
                                @Override
                                public void accept(Permission permission) throws Exception {
                                    if (permission.granted) {//同意后调用
                                        MyLogCat.i(TAG, permission.name + " is granted");
                                        choosePhoto();
                                    } else if (permission.shouldShowRequestPermissionRationale) {//禁止，但没有选择“以后不再询问”，以后申请权限，会继续弹出提示
                                        MyLogCat.i(TAG, permission.name + " is refused and will alarm next");
                                    } else {//禁止，但选择“以后不再询问”，以后申请权限，不会继续弹出提示
                                        MyLogCat.i(TAG, permission.name + " is refused and need manual set");
                                        RxjavaPermissionUtil.getInstance().alertOpenNavPermission(UserInfoActivity.this, permissionPickRequest);
                                    }
                                }
                            });
                    break;
                default:
                    break;
            }
        }
    };

    //从相册选择
    private void choosePhoto() {

    /*gxw-s for android 7    Intent pickIntent = new Intent(Intent.ACTION_PICK, null);
        pickIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(pickIntent, REQUESTCODE_PICK);gxw-e*/
        /*gxw+s for android 7*/
        Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        startActivityForResult(photoPickerIntent, REQUESTCODE_PICK);
        /*gxw+e for android 7*/
    }

    /**
     * 文件相关
     */
    private File captureFile;
    private File rootFile;
    private File cropFile;

    /**
     * 拍照
     */
    private void takePhoto() {
        //用于保存调用相机拍照后所生成的文件
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return;
        }
        //拍照获得的照片文件“将要”保存的全路径，即包括文件名+后缀
        captureFile = new File(rootFile, "temp.jpg");
        //跳转到调用系统相机
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //判断版本 如果在Android7.0以上,使用FileProvider获取Uri
        if (Build.VERSION.SDK_INT >= 24) {
            intent.setFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);

            //URI的获取需要FileProvider，即需要本APP授权，因为相机和本APP是两个应用，应用之间共享目录（或全路径）需要本APP授权
            Uri contentUri = FileProvider.getUriForFile(UserInfoActivity.this, getPackageName(), captureFile);

            //照相机将拍的照片将要输出保存在Uri下，这个Uri实质就是文件全路径captureFile
            intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
        } else {
            //否则使用Uri.fromFile(file)方法获取Uri
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(captureFile));
        }
        startActivityForResult(intent, REQUESTCODE_CAMERA);
    }



    /**
     * 裁剪图片
     * uri：将要被裁剪的照片
     */
    private void cropPhoto(Uri uri) {
        //cropFile：裁剪后的小图的保存路径
        cropFile = new File(rootFile, "avatar.jpg");
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", false);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(cropFile));
        intent.putExtra("outputFormat", Bitmap.CompressFormat.PNG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        startActivityForResult(intent, REQUESTCODE_CUTTING);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        switch (requestCode) {
            case REQUESTCODE_PICK:  //从相册里选择返回的图片
                if (data != null)
                    cropPhoto(data.getData());
                break;
            case REQUESTCODE_CAMERA:// 拍照返回的图片
                if (Build.VERSION.SDK_INT >= 24) {
                    //拍照返回后，contentUri对应的captureFile路径下就有了刚才拍照成功的照片，然后调用cropPhoto去裁剪它（contentUri下的照片）
                    Uri contentUri = FileProvider.getUriForFile(UserInfoActivity.this, getPackageName(), captureFile);
                    cropPhoto(contentUri);
                } else {
                    cropPhoto(Uri.fromFile(captureFile));
                }
                break;
            case REQUESTCODE_CUTTING://
                /*gxw+s for android 7*/
                String imgPath = cropFile.getAbsolutePath();
                saveImage(imgPath);
                Log.i(TAG,"imagePath="+imgPath);
                Bitmap avatar = BitmapFactory.decodeFile(imgPath);
                mDataBinding.eiAvatar.setImageBitmap(avatar);
                /*gxw+e for android 7*/
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    /**
     * @param path
     * @return
     */
    public String saveImage(String path) {
        if (!Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            return null;
        }
        Bitmap bitmap = BitmapFactory.decodeFile(path);
        try {
            FileOutputStream fos = new FileOutputStream(cropFile);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();

            //保存缓存的头像路径
            protectorPicFilePath = path;
            User user = new User();
            ContentValues userValues = new ContentValues();
            userValues.put("protectorPicFilePath", path);
            user.saveUserInfo(UserInfoActivity.this, userValues);

            return cropFile.getAbsolutePath();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.rl_avatar:
            case R.id.ei_avatar:
                menuWindow = new SelectPicPopupWindow(this, itemsOnClick);
                menuWindow.showAtLocation(findViewById(R.id.activity_main),
                        Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 0);
                break;

        }
    }
}

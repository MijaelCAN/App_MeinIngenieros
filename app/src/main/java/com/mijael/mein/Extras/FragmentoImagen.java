package com.mijael.mein.Extras;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Drawable;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.FileProvider;
import androidx.fragment.app.DialogFragment;

import com.mijael.mein.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class FragmentoImagen extends DialogFragment {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_IMAGE_GALLERY = 2;
    private String currentPhotoPath;
    public interface ImagePickerListener {
        void onImagePicked(Uri imageUri);
    }

    private ImagePickerListener listener;

    public FragmentoImagen(ImagePickerListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_image_picker, null);
        TextView textCamera = dialogView.findViewById(R.id.textCamera);
        TextView textGallery = dialogView.findViewById(R.id.textGaleria);

        PackageManager packageManager = getActivity().getPackageManager();

// Obtener el ícono de la aplicación de la cámara
        Intent intentCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        List<ResolveInfo> camaraActivities = packageManager.queryIntentActivities(intentCamara, PackageManager.MATCH_DEFAULT_ONLY);

        Drawable iconoCamara = null;
        for (ResolveInfo resolveInfo : camaraActivities) {
            if (resolveInfo.activityInfo.packageName.equals("Cámara")) { // Reemplaza con el nombre de paquete de la aplicación de cámara
                iconoCamara = resolveInfo.loadIcon(packageManager);
                break;
            }
        }

        // Obtener el ícono de la aplicación de la galería
        Intent intentGaleria = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        List<ResolveInfo> galeriaActivities = packageManager.queryIntentActivities(intentGaleria, PackageManager.MATCH_DEFAULT_ONLY);

        Drawable iconoGaleria = null;
        for (ResolveInfo resolveInfo : galeriaActivities) {
            if (resolveInfo.activityInfo.packageName.equals("Galería")) { // Reemplaza con el nombre de paquete de la aplicación de galería
                iconoGaleria = resolveInfo.loadIcon(packageManager);
                break;
            }
        }

        textCamera.setCompoundDrawablesRelativeWithIntrinsicBounds(iconoCamara, null, null, null);
        textGallery.setCompoundDrawablesRelativeWithIntrinsicBounds(iconoGaleria, null, null, null);


        textCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir cámara
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
                    String photoFile = null;
                    try {
                        photoFile = createImageFile();
                    } catch (IOException ex) {
                        // Se produce un errror al momento de crear un archivo
                        ex.printStackTrace();
                    }
                    // Continua si se crea correctamente
                    if (photoFile != null) {
                        Uri photoURI = FileProvider.getUriForFile(getActivity(),
                                "com.mijael.mein.fileprovider",
                                new File(photoFile)); // Crear un nuevo File con la ruta obtenida
                        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);

                        // Agregar la foto a la galería
                        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                        File f = new File(photoFile);
                        Uri contentUri = Uri.fromFile(f);
                        mediaScanIntent.setData(contentUri);
                        getActivity().sendBroadcast(mediaScanIntent);

                        MediaScannerConnection.scanFile(getActivity(), new String[]{photoFile}, null,
                                new MediaScannerConnection.OnScanCompletedListener() {
                                    @Override
                                    public void onScanCompleted(String path, Uri uri) {
                                        // Archivo escaneado con éxito
                                        // Puedes mostrar un mensaje o realizar acciones adicionales si es necesario
                                    }
                                });
                    }
                }
            }
        });

        textGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir galería
                Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pickPhoto , REQUEST_IMAGE_GALLERY);
            }
        });

        builder.setView(dialogView);

        return builder.create();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            // La imagen capturada se guarda en el Uri proporcionado al Intent de la cámara
            if (currentPhotoPath != null && listener != null) {
                listener.onImagePicked(Uri.parse(currentPhotoPath));
            }
            // Actualiza tu TextView con la imagen
        } else if (requestCode == REQUEST_IMAGE_GALLERY && resultCode == Activity.RESULT_OK) {
            // La imagen seleccionada se devuelve en el Intent data
            if (data != null && listener != null) {
                Uri imageUri = data.getData();
                listener.onImagePicked(imageUri);
            }
        }
    }

    private String createImageFile() throws IOException {
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";

        // Obtiene el directorio de almacenamiento externo
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);

        // Crea el archivo de imagen
        File imageFile = File.createTempFile(
                imageFileName,  // Nombre del archivo
                ".jpg",         // Extensión del archivo
                storageDir      // Directorio de almacenamiento
        );

        // Guarda la ruta absoluta del archivo para su uso posterior
        currentPhotoPath = imageFile.getAbsolutePath();

        return currentPhotoPath;
    }
}

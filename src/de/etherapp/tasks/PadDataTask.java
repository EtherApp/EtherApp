package de.etherapp.tasks;

import java.lang.ref.WeakReference;
import de.etherapp.app.R;
import de.etherapp.beans.PadlistItem;
import de.etherapp.epclient.Pad;

import android.os.AsyncTask;
import android.widget.TextView;

public class PadDataTask  extends AsyncTask<String, Void, String> {
	private final WeakReference<TextView> tvRef;
	private PadlistItem pli = null;
	
	public PadDataTask(TextView tv, PadlistItem pli) {
		this.tvRef = new WeakReference<TextView>(tv);
		this.pli = pli;
	}

	@Override
	// Actual download method, run in the task thread
	protected String doInBackground(String... method) {
		//download data here
		System.out.println(method[0]);
		this.pli.setUsersCount(25);
		return "25";
	}

	@Override
	// Once the image is downloaded, associates it to the imageView
	protected void onPostExecute(String result) {
		if (isCancelled()) {
			result = null;
		}

		if (tvRef != null) {
			TextView tv= tvRef.get();
			if (tv != null) {

				if (result != null) {
					tv.setText((CharSequence)result);
				} else {
					//set placeholder
				}
			}

		}
	}

	/*static Bitmap downloadBitmap(String url) {
		final AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
		final HttpGet getRequest = new HttpGet(url);
		try {
			HttpResponse response = client.execute(getRequest);
			final int statusCode = response.getStatusLine().getStatusCode();
			if (statusCode != HttpStatus.SC_OK) {
				Log.w("ImageDownloader", "Error " + statusCode
						+ " while retrieving bitmap from " + url);
				return null;
			}

			final HttpEntity entity = response.getEntity();
			if (entity != null) {
				InputStream inputStream = null;
				try {
					inputStream = entity.getContent();
					final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
					return bitmap;
				} finally {
					if (inputStream != null) {
						inputStream.close();
					}
					entity.consumeContent();
				}
			}
		} catch (Exception e) {
			// Could provide a more explicit error message for IOException or
			// IllegalStateException
			getRequest.abort();
			Log.w("ImageDownloader", "Error while retrieving bitmap from " + url);
		} finally {
			if (client != null) {
				client.close();
			}
		}
		return null;
	}*/

}
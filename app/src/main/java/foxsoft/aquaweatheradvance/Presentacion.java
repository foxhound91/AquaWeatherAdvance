package foxsoft.aquaweatheradvance;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ProgressBar;

public class Presentacion extends Activity {
	
	private ProgressBar progressBar;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lay_presentacion);
        
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);
        progressBar.setMax(100);
        progressBar.setBackgroundColor(Color.RED);
        progressBar.setProgress(0);
        
        PantallaPresentacion ATCargaDatos = new PantallaPresentacion(this);
        ATCargaDatos.execute();
    }
    
    public class PantallaPresentacion extends AsyncTask<Void, Void, Void> {
    	
        Context mContext;
        
        PantallaPresentacion(Context context) {
            mContext = context;
        }
        
        /** SHOWS A PROGRESS BAR WHEN THE APP STARTS */
        @Override
        protected Void doInBackground(Void... params) {
        	progressBar.setProgress(0);
            for (int i = 0; i < 100; i++) {
                try {
                    Thread.sleep(1);
                    progressBar.setProgress(i + 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }
        
        /** WHEN THE PROGRESS BAR GETS FULL CALLS THE ObtenerDatos CLASS */
    	@Override
    	protected void onPostExecute(Void result) {
    		mContext.startActivity(new Intent(mContext, ObtenerDatos.class));
    		finish();
    	}
        
    }
	
}
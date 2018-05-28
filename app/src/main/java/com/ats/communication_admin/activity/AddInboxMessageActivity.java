package com.ats.communication_admin.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ats.communication_admin.R;
import com.ats.communication_admin.bean.ErrorMessage;
import com.ats.communication_admin.bean.FranchiseByRoute;
import com.ats.communication_admin.bean.Route;
import com.ats.communication_admin.bean.RouteListData;
import com.ats.communication_admin.common.CommonDialog;
import com.ats.communication_admin.constants.Constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddInboxMessageActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText edTitle, edMessage, edRoute, edFranchise;
    private Button btnSubmit;
    private TextView tvRoute;

    Dialog dialog;

    private ArrayList<Route> routeArray = new ArrayList<>();
    private ArrayList<FranchiseByRoute> franchiseArray = new ArrayList<>();
    private ArrayList<String> franchiseNameArray = new ArrayList<>();

    private static Map<Integer, Boolean> map = new HashMap<Integer, Boolean>();
    private ArrayList<Integer> selectedIdArray = new ArrayList<>();
    private ArrayList<String> selectedNameArray = new ArrayList<>();
    private ArrayList<FranchiseByRoute> selectedFrArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_inbox_message);

        edTitle = findViewById(R.id.edAddInboxMsg_Title);
        edMessage = findViewById(R.id.edAddInboxMsg_Message);
        btnSubmit = findViewById(R.id.btnAddInboxMsg_Submit);
        edFranchise = findViewById(R.id.edAddInboxMsg_Fr);
        edRoute = findViewById(R.id.edAddInboxMsg_Route);
        tvRoute = findViewById(R.id.tvAddInboxMsg_Route);


        edRoute.setOnClickListener(this);
        edFranchise.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        getAllRoute();

    }


    public void getAllRoute() {
        if (Constants.isOnline(this)) {
            final CommonDialog commonDialog = new CommonDialog(this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<RouteListData> routeListDataCall = Constants.myInterface.getAllRouteList();
            routeListDataCall.enqueue(new Callback<RouteListData>() {
                @Override
                public void onResponse(Call<RouteListData> call, Response<RouteListData> response) {
                    try {
                        if (response.body() != null) {
                            RouteListData data = response.body();
                            if (data.getInfo().getError()) {
                                commonDialog.dismiss();
                                Log.e("Add Route : ", " ERROR-----" + data.getInfo().getMessage());
                            } else {
                                commonDialog.dismiss();
                                for (int i = 0; i < data.getRoute().size(); i++) {
                                    routeArray.add(data.getRoute().get(i));
                                }

                            }
                        } else {
                            commonDialog.dismiss();
                            Log.e("Add Route : ", " NULL-----");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Add Route : ", " Exception-----" + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<RouteListData> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("Add Route : ", " OnFailure-----" + t.getMessage());
                }
            });

        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }


    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.edAddInboxMsg_Route) {
            showDialog("Route");
        } else if (view.getId() == R.id.edAddInboxMsg_Fr) {
            if (tvRoute.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please Select Route", Toast.LENGTH_SHORT).show();
                edRoute.requestFocus();
            } else {
                showDialog("Fr");
            }
        } else if (view.getId() == R.id.btnAddInboxMsg_Submit) {
            if (tvRoute.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please Select Route", Toast.LENGTH_SHORT).show();
                edRoute.requestFocus();
            } else if (selectedIdArray.size() == 0) {
                Toast.makeText(this, "Please Select Franchise", Toast.LENGTH_SHORT).show();
                edFranchise.requestFocus();
            } else if (edTitle.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please Enter Title", Toast.LENGTH_SHORT).show();
                edTitle.requestFocus();
            } else if (edMessage.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please Enter Message", Toast.LENGTH_SHORT).show();
                edMessage.requestFocus();
            } else {
                sendFrNotification(selectedIdArray, edTitle.getText().toString(), edMessage.getText().toString());
            }
        }
    }


    public void showDialog(final String type) {
        dialog = new Dialog(this, android.R.style.Theme_Light_NoTitleBar);
        LayoutInflater li = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = li.inflate(R.layout.custom_dialog_list_layout, null, false);
        dialog.setContentView(v);
        dialog.setCancelable(true);
        dialog.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        ListView lvList = dialog.findViewById(R.id.lvDialog_List);
        EditText edSearch = dialog.findViewById(R.id.edDialog_Search);
        LinearLayout llButtonPanel = dialog.findViewById(R.id.llDialog_Bottom);
        Button btnDialogSubmit = dialog.findViewById(R.id.btnDialog_Submit);

        if (type.equalsIgnoreCase("Route")) {
            llButtonPanel.setVisibility(View.GONE);
            final DialogRouteListAdapter routeAdapter = new DialogRouteListAdapter(this, routeArray);
            lvList.setAdapter(routeAdapter);

            edSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {
                        if (routeAdapter != null) {
                            routeAdapter.getFilter().filter(charSequence.toString());
                        }

                    } catch (Exception e) {
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });


        } else if (type.equalsIgnoreCase("Fr")) {
            final DialogFranchiseListAdapter frAdapter = new DialogFranchiseListAdapter(this, franchiseArray);
            lvList.setAdapter(frAdapter);

            edSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    try {
                        if (frAdapter != null) {
                            frAdapter.getFilter().filter(charSequence.toString());
                        }

                    } catch (Exception e) {
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });

            btnDialogSubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    selectedIdArray.clear();
                    selectedNameArray.clear();
                    Log.e("SELECTED FR", "-------------" + selectedFrArray);
                    for (int i = 0; i < selectedFrArray.size(); i++) {
                        selectedIdArray.add(selectedFrArray.get(i).getFrId());
                        selectedNameArray.add(selectedFrArray.get(i).getFrName());
                    }
                    edFranchise.setText("" + selectedNameArray.toString());
                    Log.e("SELECTED ID", "-------------" + selectedIdArray);
                    Log.e("SELECTED NAME", "-------------" + selectedNameArray);

                    dialog.dismiss();

                }
            });


        }
        dialog.show();
    }

    public class DialogRouteListAdapter extends BaseAdapter implements Filterable {

        private ArrayList<Route> originalValues;
        private ArrayList<Route> displayedValues;
        LayoutInflater inflater;

        public DialogRouteListAdapter(Context context, ArrayList<Route> routeArrayList) {
            this.originalValues = routeArrayList;
            this.displayedValues = routeArrayList;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return displayedValues.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults results = new FilterResults();
                    ArrayList<Route> filteredArrayList = new ArrayList<Route>();

                    if (originalValues == null) {
                        originalValues = new ArrayList<Route>(displayedValues);
                    }

                    if (charSequence == null || charSequence.length() == 0) {
                        results.count = originalValues.size();
                        results.values = originalValues;
                    } else {
                        charSequence = charSequence.toString().toLowerCase();
                        for (int i = 0; i < originalValues.size(); i++) {
                            String name = originalValues.get(i).getRouteName();
                            if (name.toLowerCase().startsWith(charSequence.toString()) || name.toLowerCase().contains(charSequence.toString())) {
                                filteredArrayList.add(new Route(originalValues.get(i).getRouteId(), originalValues.get(i).getRouteName(), originalValues.get(i).getDelStatus()));
                            }
                        }
                        results.count = filteredArrayList.size();
                        results.values = filteredArrayList;
                    }

                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    displayedValues = (ArrayList<Route>) filterResults.values;
                    notifyDataSetChanged();
                }
            };

            return filter;
        }

        public class ViewHolder {
            TextView tvName;
            LinearLayout llItem;
        }

        @Override
        public View getView(final int position, View v, ViewGroup parent) {
            ViewHolder holder = null;

            if (v == null) {
                v = inflater.inflate(R.layout.custom_dialog_route_list_item, null);
                holder = new ViewHolder();
                holder.tvName = v.findViewById(R.id.tvDialogRoute_Name);
                holder.llItem = v.findViewById(R.id.llDialogRoute_Item);
                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }

            holder.tvName.setText("" + displayedValues.get(position).getRouteName());

            holder.llItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edRoute.setText("" + displayedValues.get(position).getRouteName());
                    tvRoute.setText("" + displayedValues.get(position).getRouteId());
                    getAllFranchise(displayedValues.get(position).getRouteId(), 0);

                    selectedFrArray.clear();
                    selectedNameArray.clear();
                    selectedIdArray.clear();

                    edFranchise.setText("");

                    dialog.dismiss();
                }
            });

            return v;
        }
    }

    public void getAllFranchise(int routeId, int headerId) {
        if (Constants.isOnline(this)) {
            final CommonDialog commonDialog = new CommonDialog(this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<ArrayList<FranchiseByRoute>> allFranchiseByRoute = Constants.myInterface.getAllFranchiseByRoute(routeId, headerId);
            allFranchiseByRoute.enqueue(new Callback<ArrayList<FranchiseByRoute>>() {
                @Override
                public void onResponse(Call<ArrayList<FranchiseByRoute>> call, Response<ArrayList<FranchiseByRoute>> response) {
                    try {
                        if (response.body() != null) {
                            ArrayList<FranchiseByRoute> data = response.body();
                            commonDialog.dismiss();
                            franchiseArray.clear();
                            franchiseArray = data;
                            Log.e("Franchise : ", " -----" + data);

                            if (franchiseArray.size() > 0) {
                                for (int i = 0; i < franchiseArray.size(); i++) {
                                    map.put(franchiseArray.get(i).getFrId(), false);
                                }
                            }


                        } else {
                            commonDialog.dismiss();
                            Log.e("Franchise : ", " NULL-----");
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("Franchise : ", " Exception-----" + e.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ArrayList<FranchiseByRoute>> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("Franchise : ", " OnFailure-----" + t.getMessage());
                }
            });

        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    public class DialogFranchiseListAdapter extends BaseAdapter implements Filterable {

        private ArrayList<FranchiseByRoute> originalValues;
        private ArrayList<FranchiseByRoute> displayedValues;
        LayoutInflater inflater;
        private Boolean isTouched = false;

        public DialogFranchiseListAdapter(Context context, ArrayList<FranchiseByRoute> frArrayList) {
            this.originalValues = frArrayList;
            this.displayedValues = frArrayList;
            inflater = LayoutInflater.from(context);
        }

        @Override
        public int getCount() {
            return displayedValues.size();
        }

        @Override
        public Object getItem(int i) {
            return i;
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence charSequence) {
                    FilterResults results = new FilterResults();
                    ArrayList<FranchiseByRoute> filteredArrayList = new ArrayList<FranchiseByRoute>();

                    if (originalValues == null) {
                        originalValues = new ArrayList<FranchiseByRoute>(displayedValues);
                    }

                    if (charSequence == null || charSequence.length() == 0) {
                        results.count = originalValues.size();
                        results.values = originalValues;
                    } else {
                        charSequence = charSequence.toString().toLowerCase();
                        for (int i = 0; i < originalValues.size(); i++) {
                            String name = originalValues.get(i).getFrName();
                            if (name.toLowerCase().startsWith(charSequence.toString()) || name.toLowerCase().contains(charSequence.toString())) {
                                filteredArrayList.add(new FranchiseByRoute(originalValues.get(i).getFrId(), originalValues.get(i).getFrName(), originalValues.get(i).getFrCode()));
                            }
                        }
                        results.count = filteredArrayList.size();
                        results.values = filteredArrayList;
                    }

                    return results;
                }

                @Override
                protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                    displayedValues = (ArrayList<FranchiseByRoute>) filterResults.values;
                    notifyDataSetChanged();
                }
            };

            return filter;
        }

        public class ViewHolder {
            CheckBox cbName;
        }

        @Override
        public View getView(final int position, View v, ViewGroup parent) {
            ViewHolder holder = null;

            if (v == null) {
                v = inflater.inflate(R.layout.custom_multichoice_item, null);
                holder = new ViewHolder();
                holder.cbName = v.findViewById(R.id.cbDialogFr_Name);
                v.setTag(holder);
            } else {
                holder = (ViewHolder) v.getTag();
            }

            holder.cbName.setText("" + displayedValues.get(position).getFrName());

            Boolean value = map.get(displayedValues.get(position).getFrId());
            holder.cbName.setChecked(value);

            isTouched = false;
            holder.cbName.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    isTouched = true;
                    Log.e("TRUE", "------------------------------------------------------------");
                    return false;
                }
            });

            holder.cbName.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    if (isTouched) {
                        isTouched = false;

                        if (b) {
                            map.put(displayedValues.get(position).getFrId(), true);
                            selectedFrArray.add(displayedValues.get(position));
//                            selectedIdArray.add(displayedValues.get(position).getFrId());
//                            selectedNameArray.add(displayedValues.get(position).getFrName());
                        } else {
                            map.put(displayedValues.get(position).getFrId(), false);
                            selectedFrArray.remove(displayedValues.get(position));
//                            selectedIdArray.remove(displayedValues.get(position).getFrId());
//                            selectedNameArray.remove(displayedValues.get(position).getFrName());
                        }
                    }
                }
            });


            return v;
        }
    }

    public void sendFrNotification(ArrayList<Integer> frIds, String title, String message) {
        if (Constants.isOnline(this)) {
            final CommonDialog commonDialog = new CommonDialog(this, "Loading", "Please Wait...");
            commonDialog.show();

            Call<ErrorMessage> errorMessageCall = Constants.myInterface.sendNotificationToFr(frIds, title, message);
            errorMessageCall.enqueue(new Callback<ErrorMessage>() {
                @Override
                public void onResponse(Call<ErrorMessage> call, Response<ErrorMessage> response) {
                    try {
                        if (response.body() != null) {
                            ErrorMessage data = response.body();
                            commonDialog.dismiss();
                            if (data.getError()) {
                                Log.e("FR_Notification : ", " -----" + data);
                                Toast.makeText(AddInboxMessageActivity.this, "Unable To Send Notification", Toast.LENGTH_SHORT).show();

                            } else {
                                Log.e("FR_Notification : ", " -----" + data);
                                Toast.makeText(AddInboxMessageActivity.this, "Notification Send Successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            }
                        } else {
                            commonDialog.dismiss();
                            Log.e("FR_Notification : ", " NULL-----");
                            Toast.makeText(AddInboxMessageActivity.this, "Unable To Send Notification", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        commonDialog.dismiss();
                        Log.e("FR_Notification : ", " Exception-----" + e.getMessage());
                        Toast.makeText(AddInboxMessageActivity.this, "Unable To Send Notification", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ErrorMessage> call, Throwable t) {
                    commonDialog.dismiss();
                    Log.e("FR_Notification : ", " OnFailure-----" + t.getMessage());
                    Toast.makeText(AddInboxMessageActivity.this, "Unable To Send Notification", Toast.LENGTH_SHORT).show();
                }
            });

        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }
}

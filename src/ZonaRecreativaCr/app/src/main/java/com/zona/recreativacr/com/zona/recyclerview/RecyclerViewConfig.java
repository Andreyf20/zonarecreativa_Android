package com.zona.recreativacr.com.zona.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.zona.recreativacr.R;
import com.zona.recreativacr.com.zona.data.Employee;
import com.zona.recreativacr.com.zona.data.MealPlan;
import com.zona.recreativacr.com.zona.data.MedicalStaff;
import com.zona.recreativacr.com.zona.data.Transport;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewConfig {
    private Context mContext;

    /*
    Employees config settings
     */
    public void setConfigEmployee(RecyclerView recyclerView, Context context, List<Employee> employees,
                                  IClickListener listener){
        mContext = context;
        EmployeeAdapter mEmployeeAdapter = new EmployeeAdapter(employees, listener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mEmployeeAdapter);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),
                        layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(mContext.getResources().getDrawable(R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    class EmployeeItemView extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView nameTV, idTV, insuranceTV, expireTV, vigeTV;
        IClickListener listener;

        EmployeeItemView(View itemView, IClickListener listener) {
            super(itemView);


            this.listener = listener;

            itemView.setOnClickListener(this);


            nameTV = itemView.findViewById(R.id.employee_name_textView);
            idTV = itemView.findViewById(R.id.employee_id_textView);
            insuranceTV = itemView.findViewById(R.id.employee_insurance_textView);
            expireTV = itemView.findViewById(R.id.employee_expire_textView);
            vigeTV = itemView.findViewById(R.id.employee_vigencia_textView);
        }

        void bind(Employee employee){
            // Formatting the info for easier view
            /* Formatting the values */
            String id = "Cédula: "+employee.cedula;
            String insurance = "Número de seguro: "+employee.numeroSeguro;
            /* Formatting the dates */
            @SuppressLint("SimpleDateFormat") SimpleDateFormat targetFormat =
                    new SimpleDateFormat("MM-dd-yyyy");
            String vence = "Vence: "+
                    targetFormat.format(employee.vence);
            String vige = "Vigencia: "+
                    targetFormat.format(employee.vige);
            // Setting the info
            nameTV.setText(employee.nombre);
            idTV.setText(id);
            insuranceTV.setText(insurance);
            expireTV.setText(vence);
            vigeTV.setText(vige);
        }

        @Override
        public void onClick(View v) {
            listener.OnClickObject(getAdapterPosition());
        }
    }

    class EmployeeAdapter extends RecyclerView.Adapter<EmployeeItemView> {
        private List<Employee> mEmployees;
        private IClickListener listener;

        public EmployeeAdapter(List<Employee> employees, IClickListener listener) {
            this.mEmployees = employees;
            this.listener = listener;
        }

        @NonNull
        @Override
        public EmployeeItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.employee_list_item, parent, false);
            return new EmployeeItemView(view, listener);
        }

        @Override
        public void onBindViewHolder(@NonNull EmployeeItemView holder, int position) {
            holder.bind(mEmployees.get(position));
        }

        @Override
        public int getItemCount() {
            return mEmployees.size();
        }
    }

    /*
    Transport config settings
     */

    public void setConfigTransport(RecyclerView recyclerView, Context context, List<Transport> transports){
        mContext = context;
        TransportAdapter mTransportAdapter = new TransportAdapter(transports);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mTransportAdapter);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),
                        layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(mContext.getResources().getDrawable(R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    class TransportItemView extends RecyclerView.ViewHolder {
        TextView nameTV, descriptionTV, priceTV, phoneNumberTV;

        TransportItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext)
                    .inflate(R.layout.transport_list_item, parent, false));

            nameTV = itemView.findViewById(R.id.transport_name_textView);
            descriptionTV = itemView.findViewById(R.id.transport_descripcion_textView);
            priceTV = itemView.findViewById(R.id.transport_precio_textView);
            phoneNumberTV = itemView.findViewById(R.id.transport_numeroTelefono_textView);

        }

        void bind(Transport transport){
            // Formatting the info for easier view
            /* Formatting the values */
            String price = "Precio: "+transport.precio;
            String phoneNumber = "Contacto: "+transport.numeroTelefono;
            // Setting the info
            nameTV.setText(transport.nombre);
            descriptionTV.setText(transport.descripcion);
            priceTV.setText(price);
            phoneNumberTV.setText(phoneNumber);
        }

    }

    class TransportAdapter extends RecyclerView.Adapter<TransportItemView> {
        private List<Transport> mTransports;

        public TransportAdapter(List<Transport> transports) {
            this.mTransports = transports;
        }

        @NonNull
        @Override
        public TransportItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TransportItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull TransportItemView holder, int position) {
            holder.bind(mTransports.get(position));
        }

        @Override
        public int getItemCount() {
            return mTransports.size();
        }
    }

    /*
    MealPlan config settings
     */

    public void setConfigMealPlan(RecyclerView recyclerView, Context context, List<MealPlan> mealplans){
        mContext = context;
        MealPlanAdapter mMealPlantAdapter = new MealPlanAdapter(mealplans);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mMealPlantAdapter);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),
                        layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(mContext.getResources().getDrawable(R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    class MealPlanItemView extends  RecyclerView.ViewHolder {
        TextView nameTV, desayunoTV, meriendaDesayunoTV, almuerzoTV, meriendaAlmuerzoTV, cenaTV, meriendaCenaTV;

        MealPlanItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext)
                    .inflate(R.layout.mealplan_list_item, parent, false));

            nameTV = itemView.findViewById(R.id.mealplan_name_textView);
            desayunoTV = itemView.findViewById(R.id.mealplan_desayuno_textView);
            meriendaDesayunoTV = itemView.findViewById(R.id.mealplan_meriendaDesayuno_textView);
            almuerzoTV = itemView.findViewById(R.id.mealplan_almuerzo_textView);
            meriendaAlmuerzoTV = itemView.findViewById(R.id.mealplan_meriendaAlmuerzo_textView);
            cenaTV = itemView.findViewById(R.id.mealplan_cena_textView);
            meriendaCenaTV = itemView.findViewById(R.id.mealplan_meriendaCena_textView);

        }

        void bind(MealPlan mealplan){
            // Formatting the info for easier view
            /* Formatting the values */
            String desayuno = "Desayuno: " + mealplan.desayuno;
            String meriendaDesayuno = "Merienda del desayuno: " + mealplan.meriendaDesayuno;
            String almuerzo = "Almuerzo: " + mealplan.almuerzo;
            String meriendaAlmuerzo = "Merienda del almuerzo: " + mealplan.meriendaAlmuerzo;
            String cena = "Cena: " + mealplan.cena;
            String meriendaCena = "Merienda de la cena: " + mealplan.meriendaCena;
            // Setting the info
            nameTV.setText(mealplan.nombre);
            desayunoTV.setText(desayuno);
            meriendaDesayunoTV.setText(meriendaDesayuno);
            almuerzoTV.setText(almuerzo);
            meriendaAlmuerzoTV.setText(meriendaAlmuerzo);
            cenaTV.setText(cena);
            meriendaCenaTV.setText(meriendaCena);
        }
    }

    class MealPlanAdapter extends RecyclerView.Adapter<MealPlanItemView> {
        private List<MealPlan> mMealPlans;

        public MealPlanAdapter(List<MealPlan> mealPlans) {
            this.mMealPlans = mealPlans;
        }

        @NonNull
        @Override
        public MealPlanItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MealPlanItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MealPlanItemView holder, int position) {
            holder.bind(mMealPlans.get(position));
        }

        @Override
        public int getItemCount() {
            return mMealPlans.size();
        }
    }

    /*
    MedicalStaff config settings
     */

    public void setConfigMedicalStaff(RecyclerView recyclerView, Context context, List<MedicalStaff> medicalstaffs){
        mContext = context;
        MedicalStaffAdapter mMedicalStaffAdapter = new MedicalStaffAdapter(medicalstaffs);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mMedicalStaffAdapter);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),
                        layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(mContext.getResources().getDrawable(R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    class MedicalStaffItemView extends RecyclerView.ViewHolder {
        TextView nameTV, descriptionTV, phoneNumberTV;

        MedicalStaffItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext)
                    .inflate(R.layout.medicalstaff_list_item, parent, false));

            nameTV = itemView.findViewById(R.id.medicalstaff_name_textView);
            descriptionTV = itemView.findViewById(R.id.medicalstaff_descripcion_textView);
            phoneNumberTV = itemView.findViewById(R.id.medicalstaff_numeroTelefono_textView);
        }

        void bind(MedicalStaff medicalStaff){
            // Formatting the info for easier view
            /* Formatting the values */
            String phoneNumber = "Contacto: " + medicalStaff.numeroTelefono;
            // Setting the info
            nameTV.setText(medicalStaff.nombre);
            descriptionTV.setText(medicalStaff.descripcion);
            phoneNumberTV.setText(phoneNumber);
        }
    }

    class MedicalStaffAdapter extends RecyclerView.Adapter<MedicalStaffItemView> {
        private List<MedicalStaff> mMedicalStaffs;

        public MedicalStaffAdapter(List<MedicalStaff> medicalStaffs) {
            this.mMedicalStaffs = medicalStaffs;
        }

        @NonNull
        @Override
        public MedicalStaffItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new MedicalStaffItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull MedicalStaffItemView holder, int position) {
            holder.bind(mMedicalStaffs.get(position));
        }

        @Override
        public int getItemCount() {
            return mMedicalStaffs.size();
        }
    }
}

package com.zona.recreativacr.com.zona.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zona.recreativacr.R;
import com.zona.recreativacr.com.zona.data.Employee;
import com.zona.recreativacr.com.zona.data.MealPlan;
import com.zona.recreativacr.com.zona.data.MedicalStaff;
import com.zona.recreativacr.com.zona.data.Provider;
import com.zona.recreativacr.com.zona.data.Transport;
import com.zona.recreativacr.com.zona.data.Package;

import java.text.SimpleDateFormat;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewConfig {
    private Context mContext;
    //private static RecyclerViewConfig rvc;
    //public EmployeeAdapter mEmployeeAdapter;

    /*public static RecyclerViewConfig getInstace(){
        if(rvc == null)
            rvc = new RecyclerViewConfig();
        return rvc;
    }*/

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
                    new SimpleDateFormat("d/M/yyyy");
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

    public void setConfigTransport(RecyclerView recyclerView, Context context, List<Transport> transports,
                                   IClickListener listener){
        mContext = context;
        TransportAdapter mTransportAdapter = new TransportAdapter(transports, listener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mTransportAdapter);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),
                        layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(mContext.getResources().getDrawable(R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    class TransportItemView extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView nameTV, descriptionTV, priceTV, phoneNumberTV;
        IClickListener listener;

        TransportItemView(View itemView, IClickListener listener) {
            super(itemView);

            this.listener = listener;
            itemView.setOnClickListener(this);

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

        @Override
        public void onClick(View v) {
            listener.OnClickObject(getAdapterPosition());
        }
    }

    class TransportAdapter extends RecyclerView.Adapter<TransportItemView> {
        private List<Transport> mTransports;
        private IClickListener listener;

        public TransportAdapter(List<Transport> transports, IClickListener listener) {
            this.mTransports = transports;
            this.listener = listener;
        }

        @NonNull
        @Override
        public TransportItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.transport_list_item, parent, false);
            return new TransportItemView(view, listener);
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

    public void setConfigMealPlan(RecyclerView recyclerView, Context context, List<MealPlan> mealplans,
                                  IClickListener listener){
        mContext = context;
        MealPlanAdapter mMealPlantAdapter = new MealPlanAdapter(mealplans, listener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mMealPlantAdapter);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),
                        layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(mContext.getResources().getDrawable(R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    class MealPlanItemView extends  RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView nameTV, desayunoTV, meriendaDesayunoTV, almuerzoTV, meriendaAlmuerzoTV, cenaTV, meriendaCenaTV;
        IClickListener listener;

        MealPlanItemView(View itemView, IClickListener listener) {
            super(itemView);

            this.listener = listener;
            itemView.setOnClickListener(this);

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

        @Override
        public void onClick(View v) {
            listener.OnClickObject(getAdapterPosition());
        }
    }

    class MealPlanAdapter extends RecyclerView.Adapter<MealPlanItemView> {
        private List<MealPlan> mMealPlans;
        private IClickListener listener;

        public MealPlanAdapter(List<MealPlan> mealPlans, IClickListener listener) {
            this.mMealPlans = mealPlans;
            this.listener = listener;
        }

        @NonNull
        @Override
        public MealPlanItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.mealplan_list_item, parent, false);
            return new MealPlanItemView(view, listener);
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

    public void setConfigMedicalStaff(RecyclerView recyclerView, Context context, List<MedicalStaff> medicalstaffs,
                                      IClickListener listener){
        mContext = context;
        MedicalStaffAdapter mMedicalStaffAdapter = new MedicalStaffAdapter(medicalstaffs, listener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mMedicalStaffAdapter);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),
                        layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(mContext.getResources().getDrawable(R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    class MedicalStaffItemView extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView nameTV, descriptionTV, phoneNumberTV;
        IClickListener listener;

        MedicalStaffItemView(View itemView, IClickListener listener) {
            super(itemView);

            this.listener = listener;
            itemView.setOnClickListener(this);

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

        @Override
        public void onClick(View v) {
            listener.OnClickObject(getAdapterPosition());
        }
    }

    class MedicalStaffAdapter extends RecyclerView.Adapter<MedicalStaffItemView> {
        private List<MedicalStaff> mMedicalStaffs;
        private IClickListener listener;

        public MedicalStaffAdapter(List<MedicalStaff> medicalStaffs, IClickListener listener) {
            this.mMedicalStaffs = medicalStaffs;
            this.listener = listener;
        }

        @NonNull
        @Override
        public MedicalStaffItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.medicalstaff_list_item, parent, false);
            return new MedicalStaffItemView(view, listener);
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

    public void setConfigPackages(RecyclerView recyclerView, Context context, List<Package> packages,
                                  IClickListener listener){
        mContext = context;
        PackageAdapter mPackageAdapter = new PackageAdapter(packages, listener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mPackageAdapter);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),
                        layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(mContext.getResources().getDrawable(R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    class PackageItemView extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView nameTV, capacityTV, priceTV, activeTV, breakfastTV, lunchTV, coffeTV, typeTV, descrpTV;
        IClickListener listener;

        PackageItemView(View itemView, IClickListener listener) {
            super(itemView);

            this.listener = listener;
            itemView.setOnClickListener(this);

            nameTV = itemView.findViewById(R.id.package_name_textView);
            capacityTV = itemView.findViewById(R.id.package_capacity_textView);
            priceTV = itemView.findViewById(R.id.package_price_textView);
            activeTV = itemView.findViewById(R.id.package_activo_textView);
            breakfastTV = itemView.findViewById(R.id.package_breakfast_textView);
            lunchTV = itemView.findViewById(R.id.package_almuerzo_textView);
            coffeTV = itemView.findViewById(R.id.package_cafe_textView);
            typeTV = itemView.findViewById(R.id.package_type_textView);
            descrpTV = itemView.findViewById(R.id.package_descr_textView);
        }

        void bind(Package packag){
            // Formatting the info for easier view
            /* Formatting the values */
            String activo = "Activo: "+ (packag.active ? "activo" : "No activo");
            String breakfast = "Desayuno: " + (packag.breakfast ? "activo" : "No activo");
            String lunch = "Almuerzo: " + (packag.lunch ? "activo" : "No activo");
            String coffe = "Café: " + (packag.coffe ? "activo" : "No activo");
            String precio = "Precio: " + packag.price;
            String capacidad = "Capacidad: " + packag.capacity;
            String tipo = "Tipo: " + packag.type;
            // Setting the info
            nameTV.setText(packag.name);
            capacityTV.setText(capacidad);
            priceTV.setText(precio);
            activeTV.setText(activo);
            breakfastTV.setText(breakfast);
            lunchTV.setText(lunch);
            coffeTV.setText(coffe);
            typeTV.setText(tipo);
            descrpTV.setText(packag.descrip);
        }

        @Override
        public void onClick(View v) {
            listener.OnClickObject(getAdapterPosition());
        }
    }

    class PackageAdapter extends RecyclerView.Adapter<PackageItemView> {
        private List<Package> mPackages;
        private IClickListener listener;

        public PackageAdapter(List<Package> packages, IClickListener listener) {
            this.mPackages = packages;
            this.listener = listener;
        }

        @NonNull
        @Override
        public PackageItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.package_list_item, parent, false);
            return new PackageItemView(view, listener);
        }

        @Override
        public void onBindViewHolder(@NonNull PackageItemView holder, int position) {
            holder.bind(mPackages.get(position));
        }

        @Override
        public int getItemCount() {
            return mPackages.size();
        }
    }

    public void setConfigProviders(RecyclerView recyclerView, Context context, List<Provider> providers,
                                   IClickListener listener){
        mContext = context;
        ProviderAdapter mProviderAdapter = new ProviderAdapter(providers, listener);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mProviderAdapter);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),
                        layoutManager.getOrientation());
        dividerItemDecoration.setDrawable(mContext.getResources().getDrawable(R.drawable.recyclerview_divider));
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    class ProviderItemView extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        TextView nameTV, descTV, dirTV, emailTV, phoneTV, serviceTV;
        IClickListener listener;

        ProviderItemView(View itemView, IClickListener listener) {
            super(itemView);

            this.listener = listener;
            itemView.setOnClickListener(this);

            nameTV = itemView.findViewById(R.id.provider_name_textView);
            descTV = itemView.findViewById(R.id.provider_desc_textView);
            dirTV = itemView.findViewById(R.id.provider_dir_textView);
            emailTV = itemView.findViewById(R.id.provider_email_textView);
            phoneTV = itemView.findViewById(R.id.provider_phone_textView);
            serviceTV = itemView.findViewById(R.id.provider_service_textView);
        }

        void bind(Provider provider){
            // Formatting the info for easier view
            /* Formatting the values */
            String telefono = "Teléfono: "+ provider.numeroTelefono;
            String tipoServicio = "Tipo de servicio: " + provider.tipoDeServicio;
            // Setting the info
            nameTV.setText(provider.name);
            descTV.setText(provider.descripcion);
            dirTV.setText(provider.direccion);
            emailTV.setText(provider.email);
            phoneTV.setText(telefono);
            serviceTV.setText(tipoServicio);
        }

        @Override
        public void onClick(View v) {
            listener.OnClickObject(getAdapterPosition());
        }
    }

    class ProviderAdapter extends RecyclerView.Adapter<ProviderItemView> {
        private List<Provider> mProviders;
        private IClickListener listener;

        public ProviderAdapter(List<Provider> providers, IClickListener listener) {
            this.mProviders = providers;
            this.listener = listener;
        }

        @NonNull
        @Override
        public ProviderItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.provider_list_item, parent, false);
            return new ProviderItemView(view, listener);
        }

        @Override
        public void onBindViewHolder(@NonNull ProviderItemView holder, int position) {
            holder.bind(mProviders.get(position));
        }

        @Override
        public int getItemCount() {
            return mProviders.size();
        }
    }
}

package com.zona.recreativacr.com.zona.recyclerview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zona.recreativacr.R;
import com.zona.recreativacr.com.zona.data.Employee;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewConfig {
    private Context mContext;

    public void setConfigEmployee(RecyclerView recyclerView, Context context, List<Employee> employees){
        mContext = context;
        EmployeeAdapter mEmployeeAdapter = new EmployeeAdapter(employees);
        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mEmployeeAdapter);
        DividerItemDecoration dividerItemDecoration =
                new DividerItemDecoration(recyclerView.getContext(),
                        layoutManager.getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);
    }

    class EmployeeItemView extends RecyclerView.ViewHolder {
        TextView nameTV, idTV, insuranceTV, expireTV, vigeTV;

        EmployeeItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext)
            .inflate(R.layout.employee_list_item, parent, false));


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
    }

    class EmployeeAdapter extends RecyclerView.Adapter<EmployeeItemView> {
        private List<Employee> mEmployees;

        public EmployeeAdapter(List<Employee> employees) {
            this.mEmployees = employees;
        }

        @NonNull
        @Override
        public EmployeeItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new EmployeeItemView(parent);
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
}

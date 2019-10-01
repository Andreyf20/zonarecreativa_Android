package com.zona.recreativacr.com.zona.recyclerview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.zona.recreativacr.R;
import com.zona.recreativacr.com.zona.data.Employee;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class RecyclerViewConfig {
    private Context mContext;
    private EmployeeAdapter mEmployeeAdapter;

    public void setConfigEmployee(RecyclerView recyclerView, Context context, List<Employee> employees,
                          List<String> keys){
        mContext = context;
        mEmployeeAdapter = new EmployeeAdapter(employees, keys);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(mEmployeeAdapter);
    }

    class EmployeeItemView extends RecyclerView.ViewHolder {
        TextView nameTV, idTV, insuranceTV, expireTV, vigeTV;
        String key;

        public EmployeeItemView(ViewGroup parent) {
            super(LayoutInflater.from(mContext)
            .inflate(R.layout.employee_list_item, parent, false));


            nameTV = itemView.findViewById(R.id.name_textView);
            idTV = itemView.findViewById(R.id.id_textView);
            insuranceTV = itemView.findViewById(R.id.insurance_textView);
            expireTV = itemView.findViewById(R.id.expire_textView);
            vigeTV = itemView.findViewById(R.id.vigencia_textView);
        }

        public void bind(Employee employee, String key){
            nameTV.setText(employee.nombre);
            idTV.setText(employee.cedula);
            insuranceTV.setText(employee.numeroSeguro);
            expireTV.setText(employee.vence.toString());
            vigeTV.setText(employee.vige.toString());
            this.key = key;
        }
    }

    class EmployeeAdapter extends RecyclerView.Adapter<EmployeeItemView> {
        private List<Employee> mEmployees = new ArrayList<>();
        private List<String> mKeys = new ArrayList<>();

        public EmployeeAdapter(List<Employee> employees, List<String> keys) {
            this.mEmployees = employees;
            this.mKeys = keys;
        }

        @NonNull
        @Override
        public EmployeeItemView onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new EmployeeItemView(parent);
        }

        @Override
        public void onBindViewHolder(@NonNull EmployeeItemView holder, int position) {
            holder.bind(mEmployees.get(position), mKeys.get(position));
        }

        @Override
        public int getItemCount() {
            return mEmployees.size();
        }
    }
}

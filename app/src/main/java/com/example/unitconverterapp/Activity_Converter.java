package com.example.unitconverterapp;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.unitconverterapp.databinding.ActivityConverterBinding;

public class Activity_Converter extends AppCompatActivity {
    ActivityConverterBinding binding;
    int countrySelected = -1;
    String givenUnit, requireUnit;
    float inputValue, outputValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityConverterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                binding.etInputValue.setEnabled(true);
                binding.btnConvert.setEnabled(true);
                binding.etInputValue.setText("");
                binding.tvOuputValue.setText("");

                if (checkedId == binding.rbLength.getId()) {
                    countrySelected = 1;
                    spinnerWithLengthUnits();
                } else if (checkedId == binding.rbWeight.getId()) {
                    countrySelected = 2;
                    spinnerWithWeightUnits();
                } else {
                    countrySelected = 3;
                    spinnerWithTemperatureUnits();
                }
            }
        });

        binding.btnConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!binding.etInputValue.getText().toString().trim().equals("")) {
                    givenUnit = binding.spSelectedUnits.getSelectedItem().toString();
                    requireUnit = binding.spConvertUnits.getSelectedItem().toString();
                    if (givenUnit != requireUnit) {
                        convertUnit();
                    } else {
                        binding.tvOuputValue.setText("");
                        Toast.makeText(Activity_Converter.this, "Convert unit is same as select unit", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    binding.tvOuputValue.setText("");
                    Toast.makeText(Activity_Converter.this, "Please enter value to convert", Toast.LENGTH_SHORT).show();
                }

            }
        });


    }

    private void spinnerWithLengthUnits() {
        ArrayAdapter<CharSequence> adapterLength = ArrayAdapter.createFromResource(this, R.array.length_units_array, android.R.layout.simple_spinner_item);
        adapterLength.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spSelectedUnits.setAdapter(adapterLength);
        binding.spConvertUnits.setAdapter(adapterLength);
    }

    private void spinnerWithWeightUnits() {
        ArrayAdapter<CharSequence> adapterWeight = ArrayAdapter.createFromResource(this, R.array.weight_units_array, android.R.layout.simple_spinner_item);
        adapterWeight.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spSelectedUnits.setAdapter(adapterWeight);
        binding.spConvertUnits.setAdapter(adapterWeight);

    }

    private void spinnerWithTemperatureUnits() {
        ArrayAdapter<CharSequence> adapterTemperature = ArrayAdapter.createFromResource(this, R.array.temperature_units_array, android.R.layout.simple_spinner_item);
        adapterTemperature.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spSelectedUnits.setAdapter(adapterTemperature);
        binding.spConvertUnits.setAdapter(adapterTemperature);

    }

    private void convertUnit() {
        inputValue = Float.parseFloat(binding.etInputValue.getText().toString().trim());

        switch (countrySelected) {
            case 1:
                convertLength();
                break;
            case 2:
                convertWeight();
                break;
            case 3:
                convertTemperature();
                break;
            default:
                binding.etInputValue.setText("");
                binding.tvOuputValue.setText("");

        }
    }

    private void convertLength() {
        if (givenUnit.equals("Inch") && requireUnit.equals("Foot")) {
            outputValue = (float) (inputValue / 12);
        } else if (givenUnit.equals("Inch") && requireUnit.equals("Yard")) {
            outputValue = (float) (inputValue / 36);
        } else if (givenUnit.equals("Inch") && requireUnit.equals("Mile")) {
            outputValue = (float) (inputValue / 63360);
        } else if (givenUnit.equals("Inch") && requireUnit.equals("Km")) {
            outputValue = (float) (inputValue /39370 );
        } else if (givenUnit.equals("Inch") && requireUnit.equals("Cm")) {
            outputValue = (float) (inputValue * 2.54);
        } else if (givenUnit.equals("Yard") && requireUnit.equals("Cm")) {
            outputValue = (float) (inputValue * 91.44);
        } else if (givenUnit.equals("Mile") && requireUnit.equals("Cm")) {
            outputValue = (float) (inputValue * 160900);
        } else if (givenUnit.equals("Km") && requireUnit.equals("Cm")) {
            outputValue = inputValue * 100000;
        } else if (givenUnit.equals("Foot") && requireUnit.equals("Km")) {
            outputValue = inputValue / 3281;
        } else if (givenUnit.equals("Yard") && requireUnit.equals("Km")) {
            outputValue = inputValue / 1094;
        } else if (givenUnit.equals("Yard") && requireUnit.equals("Inch")) {
            outputValue = inputValue * 36;
        } else if (givenUnit.equals("Mile") && requireUnit.equals("Km")) {
            outputValue = (float) (inputValue * 1.609);
        } else if (givenUnit.equals("Mile") && requireUnit.equals("Inch")) {
            outputValue = (float) (inputValue * 63360);
        } else if (givenUnit.equals("Cm") && requireUnit.equals("Km")) {
            outputValue = inputValue / 100000;
        } else if (givenUnit.equals("Foot") && requireUnit.equals("Yard")) {
            outputValue = inputValue / 3;
        } else if (givenUnit.equals("Km") && requireUnit.equals("Yard")) {
            outputValue = inputValue * 1094;
        } else if (givenUnit.equals("Mile") && requireUnit.equals("Yard")) {
            outputValue = inputValue * 1760;
        } else if (givenUnit.equals("Cm") && requireUnit.equals("Yard")) {
            outputValue = (float) (inputValue / 91.44);
        } else if (givenUnit.equals("Foot") && requireUnit.equals("Mile")) {
            outputValue = inputValue / 5280;
        } else if (givenUnit.equals("Foot") && requireUnit.equals("Inch")) {
            outputValue = inputValue / 12;
        } else if (givenUnit.equals("Foot") && requireUnit.equals("Cm")) {
            outputValue = (float) (inputValue * 30.48);
        } else if (givenUnit.equals("Km") && requireUnit.equals("Mile")) {
            outputValue = (float) (inputValue / 1.609);
        } else if (givenUnit.equals("Yard") && requireUnit.equals("Mile")) {
            outputValue = inputValue / 1760;
        } else if (givenUnit.equals("Cm") && requireUnit.equals("Mile")) {
            outputValue = inputValue / 160900;
        } else if (givenUnit.equals("Mile") && requireUnit.equals("Foot")) {
            outputValue = inputValue * 5280;
        } else if (givenUnit.equals("Km") && requireUnit.equals("Foot")) {
            outputValue = inputValue * 3281;
        } else if (givenUnit.equals("Km") && requireUnit.equals("Inch")) {
            outputValue = inputValue * 39370;
        } else if (givenUnit.equals("Yard") && requireUnit.equals("Foot")) {
            outputValue = inputValue * 3;
        } else if (givenUnit.equals("Cm") && requireUnit.equals("Foot")) {
            outputValue = (float) (inputValue / 30.48);
        } else if (givenUnit.equals("Cm") && requireUnit.equals("Inch")) {
            outputValue = (float) (inputValue / 2.54);
        }

        binding.tvOuputValue.setText(String.format("%.2f", outputValue));
    }

    private void convertWeight() {
        if (givenUnit.equals("Ton") && requireUnit.equals("Kg")) {
            outputValue = inputValue * 1000;
        } else if (givenUnit.equals("Ton") && requireUnit.equals("Pound")) {
            outputValue = (float) (inputValue * 2204.62);
        } else if (givenUnit.equals("Ton") && requireUnit.equals("Ounce")) {
            outputValue = (float) (inputValue * 35273.94);
        } else if (givenUnit.equals("Ton") && requireUnit.equals("Gram")) {
            outputValue = inputValue * 1000000;
        } else if (givenUnit.equals("Kg") && requireUnit.equals("Ton")) {
            outputValue = (float) (inputValue / 1000);
        } else if (givenUnit.equals("Kg") && requireUnit.equals("Pound")) {
            outputValue = (float) (inputValue * 2.21);
        } else if (givenUnit.equals("Kg") && requireUnit.equals("Ounce")) {
            outputValue = (float) (inputValue * 35.27);
        } else if (givenUnit.equals("Kg") && requireUnit.equals("Gram")) {
            outputValue = inputValue * 1000;
        } else if (givenUnit.equals("Pound") && requireUnit.equals("Ton")) {
            outputValue = (float) (inputValue / 2204.62);
        } else if (givenUnit.equals("Pound") && requireUnit.equals("Kg")) {
            outputValue = (float) (inputValue / 2.21);
        } else if (givenUnit.equals("Pound") && requireUnit.equals("Ounce")) {
            outputValue = inputValue * 16;
        } else if (givenUnit.equals("Pound") && requireUnit.equals("Gram")) {
            outputValue = (float) (inputValue * 453.60);
        } else if (givenUnit.equals("Ounce") && requireUnit.equals("Ton")) {
            outputValue = (float) (inputValue / 35273.94);
        } else if (givenUnit.equals("Ounce") && requireUnit.equals("Kg")) {
            outputValue = (float) (inputValue / 35.27);
        } else if (givenUnit.equals("Ounce") && requireUnit.equals("Pound")) {
            outputValue = inputValue / 16;
        } else if (givenUnit.equals("Ounce") && requireUnit.equals("Gram")) {
            outputValue = (float) (inputValue * 28.35);
        } else if (givenUnit.equals("Gram") && requireUnit.equals("Ton")) {
            outputValue = (float) (inputValue / 1000000);
        } else if (givenUnit.equals("Gram") && requireUnit.equals("Kg")) {
            outputValue = inputValue * 1000;
        } else if (givenUnit.equals("Gram") && requireUnit.equals("Pound")) {
            outputValue = (float) (inputValue / 453.60);
        } else if (givenUnit.equals("Gram") && requireUnit.equals("Ounce")) {
            outputValue = (float) (inputValue / 28.35);
        }

        binding.tvOuputValue.setText(String.format("%.2f", outputValue));
    }

    private void convertTemperature() {
        if (givenUnit.equals("Celsius") && requireUnit.equals("Fahrenheit")) {
            outputValue = (float) ((inputValue * 9 / 5) + 32);
        } else if (givenUnit.equals("Celsius") && requireUnit.equals("Kelvin")) {
            outputValue = (float) (inputValue + 273.15);
        } else if (givenUnit.equals("Fahrenheit") && requireUnit.equals("Celsius")) {
            outputValue = (float) ((inputValue - 32) * 5 / 9);
        } else if (givenUnit.equals("Fahrenheit") && requireUnit.equals("Kelvin")) {
            outputValue = (float) (((inputValue - 32) / 1.8) + 273.15);
        } else if (givenUnit.equals("Kelvin") && requireUnit.equals("Celsius")) {
            outputValue = (float) (inputValue - 273.15);
        } else if (givenUnit.equals("Kelvin") && requireUnit.equals("Fahrenheit")) {
            outputValue = (float) ((1.8 * (inputValue - 273)) + 32);
        }
        binding.tvOuputValue.setText(String.format("%.2f", outputValue));
    }
}
import { Controller, RegisterOptions, UseFormReturn } from 'react-hook-form';

// Material UI Components
import MuiFormControlLabel, { FormControlLabelProps } from '@material-ui/core/FormControlLabel';
import MuiCheckbox from '@material-ui/core/Checkbox';
import MuiSwitch from '@material-ui/core/Switch';
import MuiFormControl from '@material-ui/core/FormControl';
import MuiFormHelperText from '@material-ui/core/FormHelperText';

export type IBoolProps = Omit<FormControlLabelProps, 'control'> &
  Pick<UseFormReturn, 'control' | 'formState'> & {
    name: string;
    helperText?: string;
    rules?: RegisterOptions;
    type: 'checkbox' | 'switch';
  };

const Bool = ({ helperText, type, control, name, formState, rules = {}, ...props }: IBoolProps) => {
  const Child = type === 'switch' ? MuiSwitch : MuiCheckbox;
  return (
    <MuiFormControl component='fieldset' error={Boolean(formState.errors[name])} required={Boolean(rules.required)}>
      <Controller
        control={control}
        defaultValue={false}
        name={name}
        render={({ field }) => <MuiFormControlLabel {...props} checked={field.value} control={<Child color='primary' {...field} />} />}
        rules={rules}
      />
      <MuiFormHelperText>
        {formState.errors[name]?.message} {helperText}
      </MuiFormHelperText>
    </MuiFormControl>
  );
};

export default Bool;

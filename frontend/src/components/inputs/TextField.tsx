import { forwardRef } from 'react';
import { UseFormReturn } from 'react-hook-form';

// Material UI Components
import MuiTextField, { TextFieldProps } from '@material-ui/core/TextField';

export type IProps = TextFieldProps &
  Pick<UseFormReturn, 'formState'> & {
    name: string;
  };

const TextField = forwardRef(({ name, formState, ...props }: IProps, ref) => {
  return (
    <MuiTextField
      error={Boolean(formState.errors[name])}
      fullWidth
      helperText={formState.errors[name]?.message}
      InputLabelProps={{ shrink: true }}
      inputRef={ref}
      margin='normal'
      placeholder={props.placeholder || 'Skriv her'}
      variant='outlined'
      {...props}
    />
  );
});

TextField.displayName = 'TextField';
export default TextField;
